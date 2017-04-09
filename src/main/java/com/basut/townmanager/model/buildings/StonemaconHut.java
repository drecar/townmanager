package com.basut.townmanager.model.buildings;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.basut.townmanager.model.Building;
import com.basut.townmanager.model.BuildingCosts;
import com.basut.townmanager.model.UpgradeLevel;
import com.basut.townmanager.utility.TownManagerConstants;

@Entity
@DiscriminatorValue("StoneMacon")
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
		this.name = TownManagerConstants.STONEMACON_HUT;
	}

	@Override
	public boolean upgrade() {
		level = level.nextLevel();
		return false;
	}
}