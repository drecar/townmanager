package com.basut.townmanager.model;

import java.util.HashMap;
import java.util.Map;

public class LumberjacksHut extends Building {

	public LumberjacksHut() {

		Map<UpgradeLevel, BuildingCosts> upgradeTable = new HashMap<>();
		BuildingCosts buildingCosts = new BuildingCosts();
		buildingCosts.setFood(100);
		buildingCosts.setWood(100);
		buildingCosts.setStone(100);
		upgradeTable.put(UpgradeLevel.NOT_BUILT, buildingCosts);

		BuildingCosts upgradeCosts = new BuildingCosts();
		upgradeTable.put(UpgradeLevel.MIDDLE, upgradeCosts);
		this.upgradeTable = upgradeTable;
	}

	@Override
	public boolean upgrade() {
		level = level.nextLevel();
		return false;
	}

	@Override
	public void calculateOutput(Storage lager) {
		// TODO Auto-generated method stub

	}

}