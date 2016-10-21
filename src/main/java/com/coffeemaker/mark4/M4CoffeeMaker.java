package com.coffeemaker.mark4;

import com.coffeemaker.api.CoffeeMakerApi;
import com.coffeemaker.mark4.api.M4CoffeeMakerApi;
import com.coffeemaker.mark4.model.M4ContainmentVessel;
import com.coffeemaker.mark4.model.M4HotWaterSource;
import com.coffeemaker.mark4.view.M4UserInterface;

public class M4CoffeeMaker {
	public static void main(String[] args) {
		CoffeeMakerApi api = new M4CoffeeMakerApi();
		M4UserInterface userInterface = new M4UserInterface(api);
		M4HotWaterSource hotWaterSource = new M4HotWaterSource(api);
		M4ContainmentVessel containmentVessel = new M4ContainmentVessel(api);

		userInterface.init(hotWaterSource, containmentVessel);
		hotWaterSource.init(userInterface, containmentVessel);
		containmentVessel.init(userInterface, hotWaterSource);

		while (true) {
			userInterface.poll();
			hotWaterSource.poll();
			containmentVessel.poll();
		}
	}
}
