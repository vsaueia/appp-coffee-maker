package com.coffeemaker.domain;

public abstract class ContainmentVessel {
	private UserInterface userInterface;
	private HotWaterSource hotWaterSource;
	protected boolean isBrewing;
	protected boolean isComplete;

	public ContainmentVessel() {
		isBrewing = false;
		isComplete = true;
	}

	public void init(UserInterface userInterface, HotWaterSource hotWaterSource) {
		this.userInterface = userInterface;
		this.hotWaterSource = hotWaterSource;
	}

	public void start() {
		isBrewing = true;
		isComplete = false;
	}

	public void done() {
		isBrewing = false;
	}

	protected void declareComplete() {
		isComplete = true;
		userInterface.complete();
	}

	protected void containerAvailable() {
		hotWaterSource.resume();
	}

	protected void containerUnavailable() {
		hotWaterSource.pause();
	}

	public abstract boolean isReady();
}
