package com.coffeemaker.mark4.model;

import com.coffeemaker.api.CoffeeMakerApi;
import com.coffeemaker.domain.ContainmentVessel;
import com.coffeemaker.mark4.interfaces.Poolable;

import static com.coffeemaker.api.CoffeeMakerApi.*;

public class M4ContainmentVessel extends ContainmentVessel implements Poolable {
	private CoffeeMakerApi api;
	private WarmerPlateStatus lastPotStatus;

	public M4ContainmentVessel(CoffeeMakerApi api) {
		this.api = api;
		lastPotStatus = WarmerPlateStatus.POT_EMPTY;
	}

	public boolean isReady() {
		WarmerPlateStatus placeStatus = api.getWarmerPlateStatus();
		return placeStatus == WarmerPlateStatus.POT_EMPTY;
	}

	public void poll() {
		WarmerPlateStatus potStatus = api.getWarmerPlateStatus();
		if (potStatus != lastPotStatus) {
			if (isBrewing) {
				handleBrewingEvent(potStatus);
			} else if (!isComplete) {
				handleIncompleteEvent(potStatus);
			}
			lastPotStatus = potStatus;
		}
	}

	private void handleBrewingEvent(WarmerPlateStatus potStatus) {
		if (potStatus == WarmerPlateStatus.POT_NOT_EMPTY) {
			containerAvailable();
			api.setWarmerState(WarmerState.ON);
		} else if (potStatus == WarmerPlateStatus.WARMER_EMPTY) {
			containerUnavailable();
			api.setWarmerState(WarmerState.OFF);
		} else {
			containerAvailable();
			api.setWarmerState(WarmerState.OFF);
		}
	}

	private void handleIncompleteEvent(WarmerPlateStatus potStatus) {
		if (potStatus == WarmerPlateStatus.POT_NOT_EMPTY) {
			api.setWarmerState(WarmerState.ON);
		} else if (potStatus == WarmerPlateStatus.WARMER_EMPTY) {
			api.setWarmerState(WarmerState.OFF);
		} else {
			// potStatus == POT_EMPTY
			api.setWarmerState(WarmerState.OFF);
			declareComplete();
		}
	}
}
