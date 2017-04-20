package com.basut.townmanager.manager;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.basut.townmanager.model.Minion;
import com.basut.townmanager.utility.TownManagerConstants;
import com.basut.townmanager.utility.enums.BuildingName;
import com.basut.townmanager.utility.enums.TownResource;
import com.basut.townmanager.utility.enums.UpgradeLevel;

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
	
	public Map<TownResource, Long> getBuildingCosts(BuildingName name, UpgradeLevel level) {
		Map<TownResource, Long> costs = new HashMap<>();
		switch (name) {
		case FIRE_DEPARTMENT:
			costs.put(TownResource.WOOD, level.getLevelValue()*100L);
			costs.put(TownResource.FOOD, level.getLevelValue()*100L);
			costs.put(TownResource.STONE, level.getLevelValue()*300L);
			break;
		case HUNTING_HUT:
			costs.put(TownResource.WOOD, level.getLevelValue()*200L);
			costs.put(TownResource.FOOD, level.getLevelValue()*100L);
			costs.put(TownResource.STONE, level.getLevelValue()*100L);
			break;
		case LUMBERJACKS_HUT:
			costs.put(TownResource.WOOD, level.getLevelValue()*100L);
			costs.put(TownResource.FOOD, level.getLevelValue()*100L);
			costs.put(TownResource.STONE, level.getLevelValue()*100L);
			break;
		case STONEMACONS_HUT:
			costs.put(TownResource.WOOD, level.getLevelValue()*150L);
			costs.put(TownResource.FOOD, level.getLevelValue()*125L);
			costs.put(TownResource.STONE, level.getLevelValue()*100L);
			break;
		case PARK:
			costs.put(TownResource.WOOD, level.getLevelValue()*200L);
			costs.put(TownResource.FOOD, level.getLevelValue()*200L);
			costs.put(TownResource.STONE, level.getLevelValue()*50L);
			break;
		case PORTAL:
			costs.put(TownResource.RUBY, level.getLevelValue()^2*50L);
			costs.put(TownResource.FOOD, level.getLevelValue()*250L);
			costs.put(TownResource.STONE, level.getLevelValue()*500L);
			break;
		default: 
			break;
		}
		return costs;
	}
	
	public String getX() {
		return "44";
	}
}
