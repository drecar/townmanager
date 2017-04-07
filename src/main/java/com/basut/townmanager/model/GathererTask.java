package com.basut.townmanager.model;

public class GathererTask extends TownTask {

	private Building buildingAssignment;

	@Override
	public void performTownTask(Minion minion) {

		duration--;
		if (duration == 0) {
			GathererResult result = new GathererResult();
			BuildingCosts ressources = buildingAssignment.calculateOutput(minion);
			result.setRessources(ressources);

			townResult = result;
			isFinished = true;
		}
	}
}
