package com.coffeemaker.mark4.api;

import com.coffeemaker.api.CoffeeMakerApi;

public class M4CoffeeMakerApi extends CoffeeMakerApi {
    public boolean buttonPressed;
    public boolean lightOn;
    public boolean boilerOn;
    public boolean valveClosed;
    public boolean plateOn;
    public boolean boilerEmpty;
    public boolean potPresent;
    public boolean potNotEmpty;

    public M4CoffeeMakerApi() {
        buttonPressed = false;
        lightOn = false;
        boilerOn = false;
        valveClosed = true;
        plateOn = false;
        boilerEmpty = true;
        potPresent = true;
        potNotEmpty = false;
    }

    public WarmerPlateStatus getWarmerPlateStatus() {
        if (!potPresent) {
            return WarmerPlateStatus.WARMER_EMPTY;
        } else if (potNotEmpty) {
            return WarmerPlateStatus.POT_NOT_EMPTY;
        }
        return WarmerPlateStatus.POT_EMPTY;
    }

    public BoilerStatus getBoilerStatus() {
        return boilerEmpty ? BoilerStatus.EMPTY : BoilerStatus.NOT_EMPTY;
    }
    public BrewButtonStatus getBrewButtonStatus() {
        if (buttonPressed) {
            buttonPressed = false;
            return BrewButtonStatus.PUSHED;
        }
        return BrewButtonStatus.NOT_PUSHED;
    }

    public void setBoilerState(BoilerState boilerState) {
        boilerOn = boilerState == BoilerState.ON;
    }

    public void setWarmerState(WarmerState warmerState) {
        plateOn = warmerState == WarmerState.ON;
    }

    public void setIndicatorState(IndicatorState indicatorState) {
        lightOn = indicatorState == IndicatorState.ON;
    }

    public void setReliefValveState(ReliefValveState reliefValveState) {
        valveClosed = reliefValveState == ReliefValveState.CLOSED;
    }
}
