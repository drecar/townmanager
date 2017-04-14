package com.basut.townmanager.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.basut.townmanager.model.Dungeon;
import com.basut.townmanager.model.Minion;
import com.basut.townmanager.tasks.DungeonTask;
import com.basut.townmanager.tasks.GathererTask;
import com.basut.townmanager.tasks.IdleTask;
import com.basut.townmanager.tasks.TownTask;
import com.basut.townmanager.utility.enums.Skill;
import com.basut.townmanager.utility.enums.TownResource;

@Component
public class TaskManager {

	private static final Logger log = LoggerFactory.getLogger(TaskManager.class);
	
	@Autowired
	private TownManager townManager;

	@Autowired
	private MinionManager minionManager;

	private List<DungeonTask> executedTask = new ArrayList<>();

	private Random random = new Random();

	public void performTownTask(TownTask townTask, Minion minion) {
		townTask.setDuration(townTask.getDuration() - 1);
		if (townTask instanceof GathererTask) {
			performGathererTask((GathererTask) townTask, minion);
		}
		if (townTask instanceof IdleTask) {
			minionManager.healMinion(minion, 10);
			townTask.setDuration(100);
		}
		if (townTask instanceof DungeonTask) {
			performDungeonTask((DungeonTask) townTask, minion);
		}
	}

	private void performDungeonTask(DungeonTask dungeonTask, Minion minion) {
		if (executedTask.contains(dungeonTask)) {
			dungeonTask.setDuration(dungeonTask.getDuration() + 1);
		} else {
			executedTask.add(dungeonTask);
			if (dungeonTask.getDuration() < 1) {

				// wahrschweinlichkeit von events immer gleich wahrsch
				// zufallszahl zwischen 1 - 100

				StringBuilder sb = new StringBuilder();
				int encounters = dungeonTask.getDungeon().getEncounters();
				List<Minion> minionFromTask = minionManager.getMinionFromTask(dungeonTask);
				
				for (int i = 0; i < encounters; i++) {
					int nextInt = random.nextInt(10000);
					if (nextInt < 1000) {
						sb.append("Nichts passiert!").append("\n");
					} else if (nextInt < 4000) {
						List<TownResource> collectables = dungeonTask.getDungeon().getCollectables();

						int numberOfCollectables = dungeonTask.getDungeon().getDifficulty()
								* minionFromTask.stream()
										.mapToInt(min -> min.getSkillValue((Skill.GATHERING))).sum();
						townManager.getTown().getStorage().addResource(collectables.get(nextInt % collectables.size()),
								(long) numberOfCollectables);
						sb.append("Sammel ").append(collectables.get(nextInt % collectables.size())).append(" sammeln").append(numberOfCollectables).append("\n");
						log.info("Sammeln {} Sammeln {}", collectables.get(nextInt % collectables.size()), numberOfCollectables);
					} else if (nextInt < 7500) {
						sb.append("Fight!").append("\n");
					} else if (nextInt < 8500) {
						sb.append("Skill Probe").append("\n");
					} else if (nextInt < 9500) {
						sb.append("It's a Trap!").append("\n");
						//Trap macht immer 10%dmg vom aktuellen Leben
						minionFromTask.stream().forEach(this::trapDamageMinion);
					} else {
						//Priester heilt 10% vom maxLeben
						minionFromTask.stream().forEach(this::preastHealsMinion);
						sb.append("Wololo!").append("\n");
					}
				}
				dungeonTask.setFinished(true);
				System.out.println("Tasks finished:");
				System.out.println(sb.toString());
			}
		}
	}
	private void trapDamageMinion(Minion minion) {
		minion.setHealth((int)(minion.getHealth()*0.9));
	}
	
	private void preastHealsMinion(Minion minion) {
		minionManager.healMinion(minion, minion.getMaxHealth()/10);
	}

	private void performGathererTask(GathererTask townTask, Minion minion) {
		if (townTask.getDuration() < 1) {
			int levelOfBuilding = townTask.getBuildingAssignment().getUpgradeLevel().getLevelValue();
			TownResource res = townTask.getBuildingAssignment().getProducedResource();
			townManager.getTown().getStorage().addResource(res,
					(long) (levelOfBuilding * minion.getSkillValue(Skill.GATHERING)));
			townTask.setFinished(true);
		}
	}

	public void createDungeonTask(List<Minion> minionsToSendToDungeon, Dungeon dungeon) {
		DungeonTask dungeonTask = new DungeonTask();
		dungeonTask.setDungeon(dungeon);
		dungeonTask.setDuration(dungeon.getDuration());

		minionsToSendToDungeon.forEach(minion -> minion.setTask(dungeonTask));
	}

	public void performTick() {
		// perform tick
		townManager.getTown().getWorkers().stream().forEach(minion -> performTownTask(minion.getTask(), minion));

		// put minion back to idle
		List<Minion> minionsWithFinishedTasks = townManager.getTown().getWorkers().stream()
				.filter(minion -> minion.getTask().isFinished()).collect(Collectors.toList());
		minionsWithFinishedTasks.forEach(minion -> minionManager.resetTask(minion));

		// prepare next tick
		executedTask.clear();
	}
}
