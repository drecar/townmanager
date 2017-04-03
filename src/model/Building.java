package model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class Building {

	private int zustand;
	protected UpgradeLevel level = UpgradeLevel.NOT_BUILT;
	protected Map<UpgradeLevel, BuildingCosts> upgradeTable = new HashMap<>();
	protected Set<Worker> workers = new HashSet<>();

	public abstract boolean upgrade();

	public UpgradeLevel getUpgradeLevel() {
		return level;
	}

	public BuildingCosts getUpgradeCosts(UpgradeLevel level) {
		if(upgradeTable.containsKey(level)){
			return upgradeTable.get(level);
		}
		
		return new BuildingCosts();
		
	}

	public abstract void calculateOutput(Storage lager);

	public Set<Worker> getWorkers() {
		return workers;
	}

}
