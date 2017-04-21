package com.basut.townmanager.manager;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.basut.townmanager.model.Building;
import com.basut.townmanager.model.Minion;
import com.basut.townmanager.model.Town;
import com.basut.townmanager.model.buildings.FireDepartment;
import com.basut.townmanager.model.buildings.GathererBuilding;
import com.basut.townmanager.tasks.GathererTask;
import com.basut.townmanager.tasks.IdleTask;
import com.basut.townmanager.tasks.TownTask;
import com.basut.townmanager.tasks.WorkerTask;
import com.basut.townmanager.utility.enums.BuildingType;
import com.basut.townmanager.utility.enums.TownResource;
import com.basut.townmanager.utility.enums.UpgradeLevel;
import com.google.common.collect.Lists;

import lombok.Getter;

@Component
@Getter
public class TownManager {
	private Town town = new Town();
	private static final Logger log = LoggerFactory.getLogger(TownManager.class);

	@Autowired
	MinionManager minionManager;

	@Autowired
	BuildingManager buildingManager;

	@Autowired
	SetupManager setupManager;

	public TownManager() {
	}

	@PostConstruct
	public void init() {
		minionManager.initMonsters(town);
	}

	public void addWorkerToBuilding(Building building, Minion worker) {
		building.getWorkers().add(worker);
	}

	public boolean createFireDepartment() {
		FireDepartment headquater = new FireDepartment();

		if (checkAndRemoveFromStorage(headquater.getUpgradeCosts(UpgradeLevel.NOT_BUILT))) {
			town.getBuildings().add(headquater);
			System.out.println("Firedepartment hinzugef�gt.");
			return true;
		} else {
			System.out.println("Sorry, konnte deine Firedepartment nicht bauen.");
			return false;
		}

	}

	public boolean upgradeBuilding(Building building) {
		if (checkIfUpgradable(building)) {
			if (checkAndRemoveFromStorage(building.getUpgradeCosts(building.getUpgradeLevel().nextLevel()))) {

				return building.upgrade();
			}

		}
		return false;
	}

	private boolean checkIfUpgradable(Building building) {
		if (building.getUpgradeLevel() != UpgradeLevel.MAX) {

			return true;
		}

		return false;
	}

	private boolean checkAndRemoveFromStorage(Map<TownResource, Long> buildingCosts) {
		if (checkBuildingCosts(buildingCosts)) {
			buildingCosts.keySet().forEach(key -> town.getStorage().removeResource(key, buildingCosts.get(key)));
			return true;
		}
		return false;
	}

	private boolean checkBuildingCosts(Map<TownResource, Long> buildingCosts) {
		long count = buildingCosts.keySet().stream()
				.filter(key -> town.getStorage().getResource(key) < buildingCosts.get(key)).count();
		return count < 1;
	}

	public Town getTown() {
		return this.town;
	}

	/**
	 * Send a minion to work in a building.
	 * 
	 * @param minion
	 * @param building
	 */
	public void sendWorker(Minion minion, Building building) {
		TownTask task = new IdleTask();
		switch (building.getType()) {
		case GATHERER:
			task = GathererTask.builder().buildingAssignment((GathererBuilding) building).build();
			building.getWorkers().add(minion);
			break;
		case REPAIR:
			task = WorkerTask.builder().buildingAssignment(building).build();
			building.getWorkers().add(minion);
		default:
			break;
		}
		minion.setTask(task);
	}

	public boolean upgradeBuilding(long id) {
		boolean result = false;
		Optional<Building> buildingOpt = town.getBuildings().stream().filter(building -> building.getId().equals(id))
				.findFirst();
		if (buildingOpt.isPresent()) {
			Building building = buildingOpt.get();
			if(building.getUpgradeLevel().equals(UpgradeLevel.MAX)) {
				log.debug("Building ist bereits auf der höchsten Stufe");
			} else {
				Map<TownResource, Long> buildingCosts = buildingManager.getBuildingCosts(building.getName(),
						building.getLevel());
	
				long resourcesNotInStorage = buildingCosts.keySet().stream()
						.filter(key -> buildingCosts.get(key).longValue() > town.getStorage().getResource(key).longValue())
						.count();
				if (resourcesNotInStorage > 0) {
					log.info("Nicht genügend Resourcen um {} upzugraden.", building.getName().getBuildingName());
				} else {
					buildingCosts.keySet().stream()
							.forEach(key -> town.getStorage().removeResource(key, buildingCosts.get(key).longValue()));
					building.upgrade();
					result = true;
					log.info("Upgraded Buidling with id {}.", id);
				}
			}
		}
		return result;
	}
	
	public List<Building> getBuildingsToWorkAt() {
		List<BuildingType> buildingTypesToWorkAt = Lists.newArrayList(BuildingType.REPAIR,BuildingType.GATHERER);
		return town.getBuildings().stream().filter(building -> buildingTypesToWorkAt.contains(building.getType())).collect(Collectors.toList());
	}
	
}