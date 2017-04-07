package com.basut.townmanager.model;

import java.util.HashMap;
import java.util.Map;

public class StonemaconHut extends Building {

	public StonemaconHut() {

		Map<UpgradeLevel, BuildingCosts> upgradeTable = new HashMap<>();
		BuildingCosts buildingCosts = new BuildingCosts();
		buildingCosts.setFood(125);
		buildingCosts.setWood(150);
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

		// jeder Worker fügt str*lev(hut) an stein je Tick hinzu
		workers.forEach(worker -> lager.setStone(lager.getStone() + worker.getStrength() * this.level.getLevelValue()));
	}

}