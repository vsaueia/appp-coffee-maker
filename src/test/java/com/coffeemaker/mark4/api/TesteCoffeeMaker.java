package com.coffeemaker.mark4.api;

import com.coffeemaker.mark4.model.M4ContainmentVessel;
import com.coffeemaker.mark4.model.M4HotWaterSource;
import com.coffeemaker.mark4.view.M4UserInterface;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TesteCoffeeMaker {
    private M4UserInterface userInterface;
    private M4HotWaterSource hotWaterSource;
    private M4ContainmentVessel containmentVessel;
    private M4CoffeeMakerApi api;

    @Before
    public void setUp() {
        api = new M4CoffeeMakerApi();
        userInterface = new M4UserInterface(api);
        hotWaterSource = new M4HotWaterSource(api);
        containmentVessel = new M4ContainmentVessel(api);
        userInterface.init(hotWaterSource, containmentVessel);
        hotWaterSource.init(userInterface, containmentVessel);
        containmentVessel.init(userInterface, hotWaterSource);
    }

    private void poll() {
        userInterface.poll();
        hotWaterSource.poll();
        containmentVessel.poll();
    }

    @Test
    public void initialConditions() {
        poll();
        assertFalse(api.boilerOn);
        assertFalse(api.lightOn);
        assertFalse(api.plateOn);
        assertTrue(api.valveClosed);
    }

    @Test
    public void startNoPot() {
        poll();
        api.buttonPressed = true;
        api.potPresent = false;
        poll();
        assertFalse(api.boilerOn);
        assertFalse(api.lightOn);
        assertFalse(api.plateOn);
        assertTrue(api.valveClosed);
    }

    @Test
    public void startNoWater() {
        poll();
        api.buttonPressed = true;
        api.boilerEmpty = true;
        poll();
        assertFalse(api.boilerOn);
        assertFalse(api.lightOn);
        assertFalse(api.plateOn);
        assertTrue(api.valveClosed);
    }

    @Test
    public void goodStart() {
        normalStart();
        assertTrue(api.boilerOn);
        assertFalse(api.lightOn);
        assertFalse(api.plateOn);
        assertTrue(api.valveClosed);
    }

    private void normalStart() {
        poll();
        api.boilerEmpty = false;
        api.buttonPressed = true;
        poll();
    }

    @Test
    public void startedPotNotEmpty() {
        normalStart();
        api.potNotEmpty = true;
        poll();
        assertTrue(api.boilerOn);
        assertFalse(api.lightOn);
        assertTrue(api.plateOn);
        assertTrue(api.valveClosed);
    }

    @Test
    public void potRemovedAndReplacedWhileEmpty() {
        normalStart();
        api.potPresent = false;
        poll();
        assertFalse(api.boilerOn);
        assertFalse(api.lightOn);
        assertFalse(api.plateOn);
        assertFalse(api.valveClosed);

        api.potPresent = true;
        poll();
        assertTrue(api.boilerOn);
        assertFalse(api.lightOn);
        assertFalse(api.plateOn);
        assertTrue(api.valveClosed);
    }

    @Test
    public void potRemovedWhileNotEmptyAndReplacedEmpty() {
        normalFill();
        api.potPresent = false;
        poll();
        assertFalse(api.boilerOn);
        assertFalse(api.lightOn);
        assertFalse(api.plateOn);
        assertFalse(api.valveClosed);

        api.potPresent = true;
        api.potNotEmpty = false;
        poll();
        assertTrue(api.boilerOn);
        assertFalse(api.lightOn);
        assertFalse(api.plateOn);
        assertTrue(api.valveClosed);
    }

    private void normalFill() {
        normalStart();
        api.potNotEmpty = true;
        poll();
    }

    @Test
    public void potRemovedWhileNotEmptyAndReplacedNotEmpty() {
        normalFill();
        api.potPresent = false;
        poll();
        api.potPresent = true;
        poll();
        assertTrue(api.boilerOn);
        assertFalse(api.lightOn);
        assertTrue(api.plateOn);
        assertTrue(api.valveClosed);
    }

    @Test
    public void boilerEmptyPotNotEmpty() {
        normalBrew();
        assertFalse(api.boilerOn);
        assertTrue(api.lightOn);
        assertTrue(api.plateOn);
        assertTrue(api.valveClosed);
    }

    private void normalBrew() {
        normalFill();
        api.boilerEmpty = true;
        poll();
    }

    @Test
    public void boilerEmptiesWhilePotRemoved() {
        normalFill();
        api.potPresent = false;
        poll();
        api.boilerEmpty = true;
        poll();
        assertFalse(api.boilerOn);
        assertTrue(api.lightOn);
        assertFalse(api.plateOn);
        assertTrue(api.valveClosed);

        api.potPresent = true;
        poll();
        assertFalse(api.boilerOn);
        assertTrue(api.lightOn);
        assertTrue(api.plateOn);
        assertTrue(api.valveClosed);
    }

    @Test
    public void emptyPotReturnedAfter() {
        normalBrew();
        api.potNotEmpty = false;
        poll();
        assertFalse(api.boilerOn);
        assertFalse(api.lightOn);
        assertFalse(api.plateOn);
        assertTrue(api.valveClosed);
    }
}