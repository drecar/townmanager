package com.basut.townmanager.manager;

import org.junit.Test;

import com.basut.townmanager.model.Town;

public class TownManagerTest {

	private TownManager townManager = new TownManager();
	private Town town = townManager.getTown();

	@Test
	public void testUpgradeBuildingNotEnoughRessources() {

//		Building building = new FireDepartment();
//		town.getStorage().setFood(10);
//		town.getStorage().setStone(10);
//		town.getStorage().setWood(10);
//
//		townManager.upgradeBuilding(building);
//
//		assertEquals(UpgradeLevel.NOT_BUILT, building.getUpgradeLevel());
//
//		assertEquals(10, town.getStorage().getFood());
//		assertEquals(10, town.getStorage().getWood());
//		assertEquals(10, town.getStorage().getStone());
		// Upgradelevel gleich geblieben & keine Ressourcen verbraucht

	}

	@Test
	public void testUpgradeRessourcesUsed() {
//
//		Building building = new FireDepartment();
//		setHighRessources();
//
//		townManager.upgradeBuilding(building);
//
//		assertEquals(2000 - building.getUpgradeCosts(building.getUpgradeLevel()).getFood(),
//				town.getStorage().getFood());
//		assertEquals(2000 - building.getUpgradeCosts(building.getUpgradeLevel()).getWood(),
//				town.getStorage().getWood());
//		assertEquals(2000 - building.getUpgradeCosts(building.getUpgradeLevel()).getStone(),
//				town.getStorage().getStone());
//		// Ressourcen-UpgradeCosts
//		assertEquals(UpgradeLevel.VERY_LOW, building.getUpgradeLevel());
	}

	private void setHighRessources() {
//		town.getStorage().setFood(2000);
//		town.getStorage().setStone(2000);
//		town.getStorage().setWood(2000);
	}

	@Test
	public void testCalculateTurn() {
//		// town, steinhütte, worker gegeben
//		// calculate Turn
//		// lager erhält ressourcen abhängig von hütte und arbeiter
//
//		Building building = new StonemaconHut();
//		Minion worker = new Minion();
//		worker.setStrength(8);
//
//		setHighRessources();
//		building.upgrade();
//		townManager.addWorkerToBuilding(building, worker);
//		town.getBuildings().add(building);
//		TickManager tickManager = new TickManager();
//		tickManager.tick();
//
//		assertEquals(2000 + 10 + 8 * 1, town.getStorage().getStone());
	}

	@Test
	public void test() {
		// fail("Not yet implemented");
	}

}
