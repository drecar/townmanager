package model;

import java.util.HashMap;
import java.util.Map;

public abstract class Building {
	private int zustand;
//	protected BuildingCosts buildingCosts = new BuildingCosts();
	protected UpgradeLevel level = UpgradeLevel.NOT_BUILT;
	protected Map<UpgradeLevel, BuildingCosts> upgradeTable = new HashMap<>();

	public abstract boolean upgrade();

//	public BuildingCosts getBuildingCosts() {
//		return buildingCosts;
//	}

	public UpgradeLevel getUpgradeLevel() {
		return level;
	}

	public BuildingCosts getUpgradeCosts(UpgradeLevel level) {
		if(upgradeTable.containsKey(level)){
			return upgradeTable.get(level);
		}
		
		return new BuildingCosts();
		
	}

}
