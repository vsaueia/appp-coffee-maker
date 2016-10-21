package com.coffeemaker.domain;

public abstract class UserInterface {
	private HotWaterSource hotWaterSource;
	private ContainmentVessel containmentVessel;
	protected boolean isComplete;

	public UserInterface() {
		this.isComplete = true;
	}

	public void init(HotWaterSource hotWaterSource, ContainmentVessel containmentVessel) {
		this.hotWaterSource = hotWaterSource;
		this.containmentVessel = containmentVessel;
	}

	public void complete() {
		isComplete = true;
		completeCycle();
	}

	protected void startBrewing() {
		if (hotWaterSource.isReady() && containmentVessel.isReady()) {
			isComplete = false;
			hotWaterSource.start();
			containmentVessel.start();
		}
	}

	public abstract void done();
	public abstract void completeCycle();
}
