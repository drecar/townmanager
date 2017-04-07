package com.basut.townmanager.model;

import java.util.HashMap;
import java.util.Map;

public class Park extends Building {

	public Park() {

		Map<UpgradeLevel, BuildingCosts> upgradeTable = new HashMap<>();
		BuildingCosts buildingCosts = new BuildingCosts();
		buildingCosts.setFood(50);
		buildingCosts.setWood(50);
		buildingCosts.setStone(100);
		upgradeTable.put(UpgradeLevel.NOT_BUILT, buildingCosts);

		BuildingCosts upgradeCosts = new BuildingCosts();
		upgradeTable.put(UpgradeLevel.MIDDLE, upgradeCosts);
		this.upgradeTable = upgradeTable;
	}

	// TODO ben�tigt anderes Geb�ude
	// TODO Test Ressourcen abziehen

	@Override
	public boolean upgrade() {
		level = level.nextLevel();
		return false;
	}
}
