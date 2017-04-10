package com.basut.townmanager.model.buildings;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.basut.townmanager.model.Building;
import com.basut.townmanager.model.BuildingCosts;
import com.basut.townmanager.model.BuildingType;
import com.basut.townmanager.model.UpgradeLevel;
import com.basut.townmanager.utility.TownManagerConstants;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Entity
@DiscriminatorValue("LumberJack")
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
		this.name = TownManagerConstants.LUMBER_JACKS_HUT;
	}

	@Override
	public boolean upgrade() {
		level = level.nextLevel();
		return false;
	}
	
	@Override
	public BuildingType getType() {
		return BuildingType.GATHERER;
	}
}