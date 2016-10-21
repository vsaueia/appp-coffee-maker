package com.coffeemaker.mark4.view;

import com.coffeemaker.api.CoffeeMakerApi;
import com.coffeemaker.domain.UserInterface;
import com.coffeemaker.mark4.interfaces.Poolable;

import static com.coffeemaker.api.CoffeeMakerApi.BrewButtonStatus;
import static com.coffeemaker.api.CoffeeMakerApi.IndicatorState;

public class M4UserInterface extends UserInterface implements Poolable {
	private CoffeeMakerApi api;

	public M4UserInterface(CoffeeMakerApi api) {
		this.api = api;
	}

	public void poll() {
		BrewButtonStatus buttonStatus = api.getBrewButtonStatus();
		if (buttonStatus == BrewButtonStatus.PUSHED) {
			startBrewing();
		}
	}

	public void done() {
		api.setIndicatorState(IndicatorState.ON);
	}

	public void completeCycle() {
		api.setIndicatorState(IndicatorState.OFF);
	}
}
