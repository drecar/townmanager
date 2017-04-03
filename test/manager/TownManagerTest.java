package manager;

import static org.junit.Assert.assertEquals;
import model.Building;
import model.FireDepartment;
import model.Town;
import model.UpgradeLevel;

import org.junit.Test;

public class TownManagerTest {

	private Town town = new Town();
	private TownManager townManager = new TownManager(town);

	@Test
	public void testUpgradeBuilding() {

		Building building = new FireDepartment();
		town.getStorage().setFood(2000);
		town.getStorage().setStone(2000);
		town.getStorage().setWood(2000);

		townManager.upgradeBuilding(building);

		// building.upgradeLevel++;
		// storage new values

		assertEquals(UpgradeLevel.VERY_LOW, building.getUpgradeLevel());
		//
	}

	@Test
	public void testUpgradeBuildingNotEnoughRessources() {

		Building building = new FireDepartment();
		town.getStorage().setFood(10);
		town.getStorage().setStone(10);
		town.getStorage().setWood(10);

		townManager.upgradeBuilding(building);

		assertEquals(UpgradeLevel.NOT_BUILT, building.getUpgradeLevel());

		assertEquals(10, town.getStorage().getFood());
		assertEquals(10, town.getStorage().getWood());
		assertEquals(10, town.getStorage().getStone());
		// Upgradelevel gleich geblieben & keine Ressourcen verbraucht

	}

	@Test
	public void testUpgradeRessourcesUsed() {

		Building building = new FireDepartment();
		town.getStorage().setFood(2000);
		town.getStorage().setStone(2000);
		town.getStorage().setWood(2000);

		townManager.upgradeBuilding(building);

		assertEquals(2000 - building
				.getUpgradeCosts(building.getUpgradeLevel()).getFood(), town
				.getStorage().getFood());
		assertEquals(2000 - building
				.getUpgradeCosts(building.getUpgradeLevel()).getWood(), town
				.getStorage().getWood());
		assertEquals(2000 - building
				.getUpgradeCosts(building.getUpgradeLevel()).getStone(), town
				.getStorage().getStone());
		// Ressourcen-UpgradeCosts
	}

	@Test
	public void test() {
		// fail("Not yet implemented");
	}

}
