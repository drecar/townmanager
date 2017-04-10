package com.basut.townmanager.manager;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.basut.townmanager.model.Building;
import com.basut.townmanager.model.BuildingCosts;
import com.basut.townmanager.model.Minion;
import com.basut.townmanager.model.Town;
import com.basut.townmanager.model.UpgradeLevel;
import com.basut.townmanager.model.buildings.FireDepartment;
import com.basut.townmanager.tasks.GathererTask;
import com.basut.townmanager.tasks.IdleTask;
import com.basut.townmanager.tasks.TownTask;

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

	private boolean checkAndRemoveFromStorage(BuildingCosts buildingCosts) {
		if (checkBuildingCosts(buildingCosts)) {

			int food = town.getStorage().getFood() - buildingCosts.getFood();
			int wood = town.getStorage().getWood() - buildingCosts.getWood();
			int stone = town.getStorage().getStone() - buildingCosts.getStone();

			town.getStorage().setFood(food);
			town.getStorage().setWood(wood);
			town.getStorage().setStone(stone);

			return true;
		}
		return false;
	}

	private boolean checkBuildingCosts(BuildingCosts buildingCosts) {
		return (town.getStorage().getFood() >= buildingCosts.getFood()
				&& town.getStorage().getWood() >= buildingCosts.getWood()
				&& town.getStorage().getStone() >= buildingCosts.getStone());
	}

	public Town getTown() {
		return this.town;
	}

	public void addRessourcesToStorage(BuildingCosts gatheredRessources) {

		int food = town.getStorage().getFood() + gatheredRessources.getFood();
		int wood = town.getStorage().getWood() + gatheredRessources.getWood();
		int stone = town.getStorage().getStone() + gatheredRessources.getStone();

		town.getStorage().setFood(food);
		town.getStorage().setWood(wood);
		town.getStorage().setStone(stone);
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
			task = GathererTask.builder().buildingAssignment(building).build();
			building.getWorkers().add(minion);
			break;
		default:
			break;
		}
		minion.setTask(task);
	}
}
