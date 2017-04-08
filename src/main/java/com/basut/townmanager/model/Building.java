package com.basut.townmanager.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public abstract class Building {

	private int zustand;
	protected String name;
	protected UpgradeLevel level = UpgradeLevel.NOT_BUILT;
	protected Map<UpgradeLevel, BuildingCosts> upgradeTable = new HashMap<>();
	protected Set<Minion> workers = new HashSet<>();

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

	public BuildingCosts calculateOutput(Minion minion) {
		return new BuildingCosts();
	}

	public Set<Minion> getWorkers() {
		return workers;
	}

}
