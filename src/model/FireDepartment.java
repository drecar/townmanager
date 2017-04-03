package model;

import java.util.HashMap;
import java.util.Map;

public class FireDepartment extends Building {

	public FireDepartment() {

		Map<UpgradeLevel, BuildingCosts> upgradeTable = new HashMap<>();

		for (int i = 0; i >= 5; i++) {
			BuildingCosts buildingCosts = new BuildingCosts();
			buildingCosts.setFood(125 * i);
			buildingCosts.setWood(250 * i);
			buildingCosts.setStone(350 * i);
			upgradeTable.put(UpgradeLevel.levelSelect(i), buildingCosts);
		}
		BuildingCosts upgradeCosts = new BuildingCosts();
		upgradeTable.put(UpgradeLevel.MIDDLE, upgradeCosts);
		this.upgradeTable = upgradeTable;

		upgradeTable.get(1);

	}

	@Override
	public boolean upgrade() {
		level = level.nextLevel();
		return true;
	}

	@Override
	public void calculateOutput(Storage lager) {
		// TODO Auto-generated method stub

	}

}
