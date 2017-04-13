package com.basut.townmanager.manager;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.basut.townmanager.model.Minion;
import com.basut.townmanager.model.Town;
import com.basut.townmanager.repo.MinionRepository;
import com.basut.townmanager.tasks.GathererTask;
import com.basut.townmanager.tasks.IdleTask;
import com.basut.townmanager.tasks.TownTask;

@Component
public class MinionManager {

	@Autowired
	private MinionRepository minionRepository;

	@Autowired
	private TownManager townManager;

	public void initMonsters(Town town) {

	}

	public void resetTask(Minion minion) {
		TownTask oldTask = minion.getTask();
		if (oldTask instanceof GathererTask) {
			GathererTask oldGathererTask = (GathererTask) oldTask;
			oldGathererTask.getBuildingAssignment().getWorkers().remove(minion);
		}
		minion.setTask(new IdleTask());
	}

	public void restoreHealth(Minion minion, long health) {

	}

	public List<Minion> getIdleMinions() {
		return townManager.getTown().getWorkers().stream().filter(minion -> minion.getTask() instanceof IdleTask)
				.collect(Collectors.toList());
	}

}
