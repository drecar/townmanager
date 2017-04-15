package com.basut.townmanager.model.buildings;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.basut.townmanager.model.Building;
import com.basut.townmanager.utility.enums.BuildingName;
import com.basut.townmanager.utility.enums.BuildingType;

import lombok.ToString;

@ToString
@Entity
@DiscriminatorValue("FireDepartment")
public class FireDepartment extends Building {

	public FireDepartment() {
		this.name = BuildingName.FIRE_DEPARTMENT;
//		Map<UpgradeLevel, BuildingCosts> upgradeTable = new HashMap<>();
//
//		for (int i = 0; i >= 5; i++) {
//			BuildingCosts buildingCosts = new BuildingCosts();
//			buildingCosts.setFood(125 * i);
//			buildingCosts.setWood(250 * i);
//			buildingCosts.setStone(350 * i);
//			upgradeTable.put(UpgradeLevel.levelSelect(i), buildingCosts);
//		}
//
//		BuildingCosts upgradeCosts = new BuildingCosts();
//		upgradeTable.put(UpgradeLevel.MIDDLE, upgradeCosts);
//		this.upgradeTable = upgradeTable;
//
//		upgradeTable.get(1);
	}

	@Override
	public BuildingType getType() {
		return BuildingType.REPAIR;
	}
}
