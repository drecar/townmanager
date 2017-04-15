package com.basut.townmanager.manager;

import java.util.Map;

import javax.annotation.PostConstruct;

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
import com.basut.townmanager.utility.enums.TownResource;
import com.basut.townmanager.utility.enums.UpgradeLevel;

import lombok.Getter;

@Component
@Getter
public class TownManager {
	private Town town = new Town();

	@Autowired
	MinionManager minionManager;

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

	private boolean checkAndRemoveFromStorage(Map<TownResource,Long> buildingCosts) {
		if (checkBuildingCosts(buildingCosts)) {
			buildingCosts.keySet().forEach(key -> town.getStorage().removeResource(key, buildingCosts.get(key)));
			return true;
		}
		return false;
	}

	private boolean checkBuildingCosts(Map<TownResource,Long> buildingCosts) {
		long count = buildingCosts.keySet().stream().filter(key ->town.getStorage().getResource(key)<buildingCosts.get(key)).count();
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
			task = GathererTask.builder().buildingAssignment((GathererBuilding)building).build();
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


}
