package com.basut.townmanager.utility.enums;

import com.basut.townmanager.utility.TownManagerConstants;

import lombok.Getter;

@Getter
public enum BuildingName {
	HUNTING_HUT(TownManagerConstants.HUNTING_HUT), FIRE_DEPARTMENT(TownManagerConstants.FIRE_DEPARTMENT), PARK(
			TownManagerConstants.PARK), LUMBERJACKS_HUT(
					TownManagerConstants.LUMBER_JACKS_HUT), STONEMACONS_HUT(TownManagerConstants.STONEMACON_HUT), STORAGE(TownManagerConstants.STORAGE);
	private String buildingName;

	BuildingName(String name) {
		this.buildingName = name;
	}
}
