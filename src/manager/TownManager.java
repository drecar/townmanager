package manager;

import model.Building;
import model.BuildingCosts;
import model.FireDepartment;
import model.Storage;
import model.Town;
import model.UpgradeLevel;
import model.Worker;

public class TownManager {
	private Town town;

	public TownManager(Town town) {
		this.town = town;

	}

	public void calculateTurn() {
		Storage lager = town.getStorage();
		calculateBasics(lager);
		calculateRessourcesFromBuildings(lager);
	}

	private void calculateRessourcesFromBuildings(Storage lager) {
		town.getBuildings().stream().forEach(building -> {
			building.calculateOutput(lager);
		});
		
	}

	private void calculateBasics(Storage lager) {
		lager.setFood(lager.getFood() + 10);
		lager.setWood(lager.getWood() + 10);
		lager.setStone(lager.getStone() + 10);
	}


	public void addWorkerToBuilding(Building building, Worker worker) {

		building.getWorkers().add(worker);
	}

	public boolean createFireDepartment() {
		FireDepartment headquater = new FireDepartment();

		if (checkAndRemoveFromStorage(headquater.getUpgradeCosts(UpgradeLevel.NOT_BUILT))) {
			town.getBuildings().add(headquater);
			System.out.println("Firedepartment hinzugef�gt.");
			return true;
		} else {
			System.out
					.println("Sorry, konnte deine Firedepartment nicht bauen.");
			return false;
		}

	}

	public boolean upgradeBuilding(Building building) {
		if (checkIfUpgradable(building)) {
			if (checkAndRemoveFromStorage(building.getUpgradeCosts(building
					.getUpgradeLevel().nextLevel()))) {

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
				&& town.getStorage().getWood() >= buildingCosts.getWood() && town
				.getStorage().getStone() >= buildingCosts.getStone());
	}
}
