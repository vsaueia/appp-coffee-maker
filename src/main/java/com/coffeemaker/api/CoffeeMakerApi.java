package com.coffeemaker.api;

public abstract class CoffeeMakerApi {
	public abstract void setIndicatorState(IndicatorState status);//
	public abstract BrewButtonStatus getBrewButtonStatus();//
	public abstract BoilerStatus getBoilerStatus();//
	public abstract void setReliefValveState(ReliefValveState state);//
	public abstract void setBoilerState(BoilerState state);//
	public abstract WarmerPlateStatus getWarmerPlateStatus();//
	public abstract void setWarmerState(WarmerState state);//

	public enum BrewButtonStatus {
		PUSHED, NOT_PUSHED
	}

	public enum IndicatorState {
		OFF, ON
	}

	public enum BoilerStatus {EMPTY, NOT_EMPTY}

	public enum ReliefValveState {OPEN, CLOSED}

	public enum BoilerState {OFF, ON}

	public enum WarmerPlateStatus {POT_NOT_EMPTY, WARMER_EMPTY, POT_EMPTY}

	public enum WarmerState {OFF, ON}
}
