package com.basut.townmanager.manager;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.basut.townmanager.model.Minion;
import com.basut.townmanager.model.buildings.Storage;
import com.basut.townmanager.tasks.TownTask;
import com.basut.townmanager.utility.enums.Resources;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class TickManager {

	@Autowired
	private TownManager townManager;

	@Autowired
	private MinionManager minionManager;
	
	@Autowired
	private BuildingManager buildingManager;
	
	@Autowired
	private TaskManager taskManager;

	public void tick() {
		Storage lager = townManager.getTown().getStorage();
		
		calculateBasics(lager);
		
		//Taks ausführen
		townManager.getTown().getWorkers().stream().forEach(minion -> taskManager.performTownTask(minion.getTask(), minion));

		List<TownTask> finishedTasks = townManager.getTown().getWorkers().stream().map(worker -> worker.getTask())
				.filter(task -> task.isFinished()).collect(Collectors.toList());

		List<Minion> minionsWithFinishedTasks = townManager.getTown().getWorkers().stream()
				.filter(minion -> minion.getTask().isFinished()).collect(Collectors.toList());
		
		taskManager.collectGathererTask(minionsWithFinishedTasks);
	}

	private void calculateBasics(Storage lager) {
		lager.addResource(Resources.FOOD, 10L);
		lager.addResource(Resources.WOOD, 10L);
		lager.addResource(Resources.STONE, 10L);
	}

}
