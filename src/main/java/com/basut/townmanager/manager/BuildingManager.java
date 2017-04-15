package com.basut.townmanager.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.basut.townmanager.model.Minion;
import com.basut.townmanager.utility.TownManagerConstants;

@Component
public class BuildingManager {

	@Autowired
	TownManager townManager;

	public void decayBuildings() {
		townManager.getTown().getBuildings().forEach(building -> building.setZustand(building.getZustand() - 1));
	}

	public void restoreBuildings(int levelValue, Minion minion) {
		townManager.getTown().getBuildings().forEach(building -> {
			int newCondition = building.getZustand() + levelValue;
			if (building.getZustand() >= TownManagerConstants.MAX_BUILDING_CONDITION) {
				newCondition = TownManagerConstants.MAX_BUILDING_CONDITION;
			}
			building.setZustand(newCondition);
		});

	}

}
