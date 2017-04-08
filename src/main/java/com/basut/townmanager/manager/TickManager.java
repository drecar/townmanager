package com.basut.townmanager.manager;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.basut.townmanager.model.BuildingCosts;
import com.basut.townmanager.model.buildings.Storage;

import cam.basut.townmanager.tasks.GathererResult;
import cam.basut.townmanager.tasks.GathererTask;
import cam.basut.townmanager.tasks.TownTask;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class TickManager {

	@Autowired
	private TownManager townManager;
	// private Town town = townManager.getTown();

	public void tick() {
		/*
		 * clacBasics jeder Minion im Dorf übt Task aus unterscheidung wlecher
		 * task
		 */

		Storage lager = townManager.getTown().getStorage();
		calculateBasics(lager);

		townManager.getTown().getWorkers().stream().forEach(minion -> minion.getTask().performTownTask(minion));

		List<TownTask> finishedTasks = townManager.getTown().getWorkers().stream().map(worker -> worker.getTask())
				.filter(task -> task.isFinished()).collect(Collectors.toList());

		collectGathererTask(finishedTasks);
	}

	private void collectGathererTask(List<TownTask> finishedTasks) {

		List<GathererTask> gathererTasks = finishedTasks.stream().filter(task -> (task instanceof GathererTask))
				.map(task -> (GathererTask) task).collect(Collectors.toList());
		BuildingCosts gatheredRessources = new BuildingCosts();
		gathererTasks.forEach(
				task -> gatheredRessources.addBuildingCosts(((GathererResult) task.getTownResult()).getRessources()));
		townManager.addRessourcesToStorage(gatheredRessources);
	}

	private void calculateBasics(Storage lager) {
		lager.setFood(lager.getFood() + 10);
		lager.setWood(lager.getWood() + 10);
		lager.setStone(lager.getStone() + 10);
	}

}
