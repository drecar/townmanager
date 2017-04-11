package com.basut.townmanager.manager;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.basut.townmanager.model.Minion;
import com.basut.townmanager.tasks.GathererTask;
import com.basut.townmanager.tasks.IdleTask;
import com.basut.townmanager.tasks.TownTask;
import com.basut.townmanager.utility.enums.Resources;

@Component
public class TaskManager {

	@Autowired
	private TownManager townManager;

	@Autowired
	private MinionManager minionManager;

	public void collectGathererTask(List<Minion> minionsWithFinishedTasks) {
		List<Minion> minionsWithFinishedGathererTask = minionsWithFinishedTasks.stream()
				.filter(minion -> (minion.getTask() instanceof GathererTask)).collect(Collectors.toList());

		// put Monsters back to idle
		minionsWithFinishedGathererTask.forEach(minion -> minionManager.resetTask(minion));
	}

	public void performTownTask(TownTask townTask, Minion minion) {
		townTask.setDuration(townTask.getDuration() - 1);
		if (townTask instanceof GathererTask) {
			performGathererTask((GathererTask) townTask, minion);
		}
		if (townTask instanceof IdleTask) {
			minionManager.restoreHealth(minion, 10L);
			townTask.setDuration(100);
		}
	}

	private void performGathererTask(GathererTask townTask, Minion minion) {
		if (townTask.getDuration() < 1) {
			int levelOfBuilding = townTask.getBuildingAssignment().getUpgradeLevel().getLevelValue();
			Resources res = townTask.getBuildingAssignment().getProducedResource();
			townManager.getTown().getStorage().addResource(res, (long) (levelOfBuilding * minion.getStrength()));
			townTask.setFinished(true);
		}
	}
}
