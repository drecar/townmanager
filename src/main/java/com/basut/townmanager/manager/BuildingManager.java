package com.basut.townmanager.manager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.basut.townmanager.model.Building;
import com.basut.townmanager.model.Minion;
import com.basut.townmanager.model.Town;
import com.basut.townmanager.model.buildings.FireDepartment;
import com.basut.townmanager.model.buildings.GathererBuilding;
import com.basut.townmanager.model.buildings.Park;
import com.basut.townmanager.utility.TownManagerConstants;
import com.basut.townmanager.utility.enums.BuildingName;
import com.basut.townmanager.utility.enums.TownResource;

@Component
public class BuildingManager {

	@Autowired
	TownManager townManager;

	public void decayBuildings(Town town) {
		town.getBuildings().forEach(building -> building.setZustand(building.getZustand() - 1));
	}

	// public allowedBuildingList
	public void restoreBuildings(int levelValue, Minion minion) {
		townManager.getTown().getBuildings().forEach(building -> {
			int newCondition = building.getZustand() + levelValue;
			if (building.getZustand() >= TownManagerConstants.MAX_BUILDING_CONDITION) {
				newCondition = TownManagerConstants.MAX_BUILDING_CONDITION;
			}
			building.setZustand(newCondition);
		});
	}

	public Map<TownResource, Long> getBuildingCosts(BuildingName name, int level) {
		Map<TownResource, Long> costs = new HashMap<>();
		int nextLevel = level + 1;
		switch (name) {
		case FIRE_DEPARTMENT:
			costs.put(TownResource.WOOD, nextLevel * 100L);
			costs.put(TownResource.FOOD, nextLevel * 100L);
			costs.put(TownResource.STONE, nextLevel * 300L);
			break;
		case HUNTING_HUT:
			costs.put(TownResource.WOOD, nextLevel * 200L);
			costs.put(TownResource.FOOD, nextLevel * 100L);
			costs.put(TownResource.STONE, nextLevel * 100L);
			break;
		case LUMBERJACKS_HUT:
			costs.put(TownResource.WOOD, nextLevel * 100L);
			costs.put(TownResource.FOOD, nextLevel * 100L);
			costs.put(TownResource.STONE, nextLevel * 100L);
			break;
		case STONEMACONS_HUT:
			costs.put(TownResource.WOOD, nextLevel * 150L);
			costs.put(TownResource.FOOD, nextLevel * 125L);
			costs.put(TownResource.STONE, nextLevel * 100L);
			break;
		case PARK:
			costs.put(TownResource.WOOD, nextLevel * 200L);
			costs.put(TownResource.FOOD, nextLevel * 200L);
			costs.put(TownResource.STONE, nextLevel * 50L);
			break;
		case PORTAL:
			costs.put(TownResource.RUBY, nextLevel ^ 2 * 50L);
			costs.put(TownResource.FOOD, nextLevel * 250L);
			costs.put(TownResource.STONE, nextLevel * 500L);
			break;
		default:
			break;
		}
		return costs;
	}

	public boolean checkBuildingRequirements(BuildingName name) {
		Map<BuildingName, Integer> requirements = new HashMap<>();
		switch (name) {
		case TOWNHALL:
			requirements.put(BuildingName.TOWNHALL, 0);
		case FIRE_DEPARTMENT:
			requirements.put(BuildingName.TOWNHALL, 1);
			requirements.put(BuildingName.LUMBERJACKS_HUT, 3);
			requirements.put(BuildingName.STONEMACONS_HUT, 5);
			break;
		case HUNTING_HUT:
			requirements.put(BuildingName.TOWNHALL, 1);
			break;
		case LUMBERJACKS_HUT:
			requirements.put(BuildingName.TOWNHALL, 1);
			break;
		case STONEMACONS_HUT:
			requirements.put(BuildingName.TOWNHALL, 1);
			break;
		case PARK:
			requirements.put(BuildingName.TOWNHALL, 1);
			break;
		case PORTAL:
			requirements.put(BuildingName.TOWNHALL, 1);
			break;
		default:
			break;
		}

		if (requirements.keySet().stream().filter(key -> !checkBuildingLevels(key, requirements.get(key)))
				.count() > 0) {
			return false;
		}
		return true;
	}

	public List<Building> getBuildingsToBuild() {

		Town town = townManager.getTown();
		BuildingName[] allBuildings = BuildingName.values();
		List<BuildingName> upgradableBuildings = Arrays.asList(allBuildings);
		List<BuildingName> buildableBuildings = upgradableBuildings.stream()
				.filter(value -> checkBuildingRequirements(value) == true).collect(Collectors.toList());

		List<BuildingName> buildingsBuild = town.getBuildings().stream().map(building -> building.getName())
				.collect(Collectors.toList());
		buildableBuildings.stream().filter(name -> !buildingsBuild.contains(name))
				.forEach(name -> restoreBuildingFromName(name, town));

		townManager.saveTown(town);
		return town.getBuildings();

	}

	private void restoreBuildingFromName(BuildingName name, Town town) {

		Building building;
		switch (name) {
		case FIRE_DEPARTMENT:
			town.getBuildings().add(new FireDepartment());
			break;
		case HUNTING_HUT:
			town.getBuildings().add(new GathererBuilding(name, TownResource.FOOD));
			break;
		case LUMBERJACKS_HUT:
			town.getBuildings().add(new GathererBuilding(name, TownResource.WOOD));
			break;
		case PARK:
			town.getBuildings().add(new Park());
			break;
		case STONEMACONS_HUT:
			town.getBuildings().add(new GathererBuilding(name, TownResource.STONE));
			break;
		// case STORAGE:
		// town.getBuildings().add(new Storage());
		// break;
		// case TOWNHALL:
		// town.getBuildings().add(new Townhall());
		// break;
		default:
			break;

		}
	}

	/**
	 * checks if the given building is on the level or above
	 * 
	 * @param name
	 *            name of the building
	 * @param level
	 *            wanted level of the building
	 * @return true if level of building is high enough
	 */
	public boolean checkBuildingLevels(BuildingName name, int level) {
		return townManager.getTown().getBuildings().stream().filter(building -> building.getName().equals(name))
				.filter(building -> building.getLevel() >= level).count() > 0;
	}

	public String getX() {
		return "44";
	}
}
