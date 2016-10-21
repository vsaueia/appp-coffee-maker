package com.coffeemaker.domain;

public abstract class HotWaterSource {
	private UserInterface userInterface;
	private ContainmentVessel containmentVessel;
	protected boolean isBrewing;

	public HotWaterSource() {
		this.isBrewing = false;
	}

	public void init(UserInterface userInterface, ContainmentVessel containmentVessel) {
		this.userInterface = userInterface;
		this.containmentVessel = containmentVessel;
	}

	public void start() {
		this.isBrewing = true;
		startBrewing();
	}

	public void done() {
		this.isBrewing = false;
	}

	protected void declareDone() {
		userInterface.done();
		containmentVessel.done();
		isBrewing = false;
	}

	public abstract boolean isReady();
	public abstract void startBrewing();
	public abstract void pause();
	public abstract void resume();
}
