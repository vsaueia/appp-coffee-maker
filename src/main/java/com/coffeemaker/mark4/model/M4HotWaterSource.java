package com.coffeemaker.mark4.model;

import com.coffeemaker.api.CoffeeMakerApi;
import com.coffeemaker.domain.HotWaterSource;
import com.coffeemaker.mark4.interfaces.Poolable;

import static com.coffeemaker.api.CoffeeMakerApi.*;

public class M4HotWaterSource extends HotWaterSource implements Poolable {
	private CoffeeMakerApi api;

	public M4HotWaterSource(CoffeeMakerApi api) {
		this.api = api;
	}

	public boolean isReady() {
		BoilerStatus boilerStatus = api.getBoilerStatus();
		return boilerStatus == BoilerStatus.NOT_EMPTY;
	}

	public void startBrewing() {
		api.setReliefValveState(ReliefValveState.CLOSED);
		api.setBoilerState(BoilerState.ON);
	}

	public void poll() {
		BoilerStatus boilerStatus = api.getBoilerStatus();

		if (isBrewing) {
			if (boilerStatus == BoilerStatus.EMPTY) {
				api.setBoilerState(BoilerState.OFF);
				api.setReliefValveState(ReliefValveState.CLOSED);
				declareDone();
			}
		}
	}

	public void pause() {
		api.setBoilerState(BoilerState.OFF);
		api.setReliefValveState(ReliefValveState.OPEN);
	}

	public void resume() {
		api.setBoilerState(BoilerState.ON);
		api.setReliefValveState(ReliefValveState.CLOSED);
	}
}
