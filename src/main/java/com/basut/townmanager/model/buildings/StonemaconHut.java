package com.basut.townmanager.model.buildings;

import java.util.HashMap;
import java.util.Map;

import com.basut.townmanager.model.Building;
import com.basut.townmanager.model.BuildingCosts;
import com.basut.townmanager.model.UpgradeLevel;

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
}