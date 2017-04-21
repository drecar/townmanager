package com.basut.townmanager.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
import com.basut.townmanager.tasks.WorkerTask;
import com.basut.townmanager.utility.TownManagerConstants;
import com.basut.townmanager.utility.enums.AttackType;
import com.basut.townmanager.utility.enums.BuildingType;
import com.basut.townmanager.utility.enums.Skill;
import com.basut.townmanager.utility.enums.TownResource;

@Component
public class TaskManager {

	private static final Logger log = LoggerFactory.getLogger(TaskManager.class);

	@Autowired
	private TownManager townManager;

	@Autowired
	private MinionManager minionManager;

	@Autowired
	private BuildingManager buildingManager;

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
		if (townTask instanceof WorkerTask) {
			performWorkerTask((WorkerTask) townTask, minion);
		}
	}

	private void performWorkerTask(WorkerTask workerTask, Minion minion) {
		BuildingType type = workerTask.getBuildingAssignment().getType();
		switch (type) {
		case REPAIR:
			buildingManager.restoreBuildings(workerTask.getBuildingAssignment().getLevel(), minion);
			workerTask.setDuration(100);
			break;
		default:
			break;
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
				List<Minion> minionsFromTask = minionManager.getMinionFromTask(dungeonTask);

				for (int i = 0; i < encounters; i++) {
					int nextInt = random.nextInt(10000);
					if (nextInt < 1000) {
						sb.append("Nichts passiert!").append("\n");
					} else if (nextInt < 4000) {
						log.info("Gatheringevent");
						int sumOfGatheringSkills = minionsFromTask.stream()
								.mapToInt(min -> min.getSkillValue((Skill.GATHERING))).sum();
						distributeResources(dungeonTask.getDungeon(), minionsFromTask, sumOfGatheringSkills);

					} else if (nextInt < 7500) {

						fightevent(dungeonTask.getDungeon(), minionsFromTask);

						sb.append("Fight!").append("\n");
					} else if (nextInt < 8500) {
						sb.append("Skill Probe").append("\n");
					} else if (nextInt < 9500) {
						sb.append("It's a Trap!").append("\n");
						// Trap macht immer 10%dmg vom aktuellen Leben
						minionsFromTask.stream().forEach(this::trapDamageMinion);
					} else {
						// Priester heilt 10% vom maxLeben
						minionsFromTask.stream().forEach(this::preastHealsMinion);
						sb.append("Wololo!").append("\n");
					}
				}
				dungeonTask.setFinished(true);
				System.out.println("Tasks finished:");
				System.out.println(sb.toString());
			}
		}
	}

	private void distributeResources(Dungeon dungeon, List<Minion> minionsFromTask, int collectingFactor) {
		List<TownResource> collectables = dungeon.getCollectables();
		int numberOfCollectables = dungeon.getDifficulty() * collectingFactor;
		int nextInt = Math.abs(random.nextInt());
		townManager.getTown().getStorage().addResource(collectables.get(nextInt % collectables.size()),
				(long) numberOfCollectables);
		log.info("Adding {}{} to Storage", numberOfCollectables, collectables.get(nextInt % collectables.size()));
	}

	private void fightevent(Dungeon dungeon, List<Minion> minions) {
		// Gegnergruppe generieren
		Map<AttackType, Integer> damageFromEnemy = new HashMap<>();
		final int totalDamage = (dungeon.getDifficulty() ^ 2 * 10) * (5000 + random.nextInt(10001)) / 10000;
		dungeon.getCreatureElements().stream()
				.forEach(key -> damageFromEnemy.put(key, totalDamage / dungeon.getCreatureElements().size()));
		int healthFromOpponent = (dungeon.getDifficulty() ^ 2 * 300) * (5000 + random.nextInt(10001)) / 10000;
		Map<AttackType, Double> resistanceOfEnemies = new HashMap<>();
		dungeon.getCreatureElements().stream().forEach(key -> resistanceOfEnemies.put(key, random.nextDouble()));
		log.info("Monstergroup with {} Health and  {} Damage appears", healthFromOpponent, totalDamage);

		boolean teamRemains = minions.stream().anyMatch(minion -> minion.getHealth() > 0);
		boolean enemyRemains = healthFromOpponent > 0;

		while (teamRemains && enemyRemains) {
			// Schaden bestimmen
			// Schaden Spielergruppe
			Map<AttackType, Integer> damage1 = new HashMap<>();
			minions.stream().filter(minion -> minion.getHealth() > 0).forEach(minion -> {
				minion.getAttackElements().keySet().stream().forEach(key -> {
					if (damage1.containsKey(key)) {
						damage1.put(key, damage1.get(key) + minion.getAttackElements().get(key));
					} else {
						damage1.put(key, minion.getAttackElements().get(key));
					}
				});

			});

			// Distribute damage to minions according to their resistance and
			// maxhealth
			int totalHealth1 = minions.stream().filter(minion -> minion.getHealth() > 0)
					.mapToInt(minion -> minion.getMaxHealth()).sum();
			minions.stream().forEach(minion -> {
				distributeDamageAccordingly(damageFromEnemy, totalHealth1, minion);
			});

			// Distribute damage to opponent
			healthFromOpponent -= damage1.keySet().stream()
					.mapToInt(key -> (int) (damage1.get(key)
							* Optional.ofNullable(resistanceOfEnemies.get(key)).orElseGet(() -> new Double(1.0))))
					.sum();

			// 5. Sieg/Niederlage/ Listen&Repeat
			teamRemains = minions.stream().anyMatch(minion -> minion.getHealth() > 0);
			enemyRemains = healthFromOpponent > 0;
			log.info("Remaining Health of Oponent {}", healthFromOpponent);
			log.info("End of Round.");
		}
		if (teamRemains) {
			distributeResources(dungeon, minions, dungeon.getDifficulty() * 50);
			distributeResources(dungeon, minions, dungeon.getDifficulty() * 50);
			// exp&lvup
		}

	}

	private void distributeDamageAccordingly(Map<AttackType, Integer> damageFromEnemy, int totalHealth1,
			Minion minion) {
		double factorMaxDamage = (double) minion.getMaxHealth() / totalHealth1;
		Map<AttackType, Integer> damageForMinion = new HashMap<>();
		damageFromEnemy.keySet().stream()
				.forEach(key -> damageForMinion.put(key, (int) (factorMaxDamage * damageFromEnemy.get(key))));
		minionManager.dealDamageToMinion(minion, damageForMinion);
	}

	private void trapDamageMinion(Minion minion) {
		minion.setHealth((int) (minion.getHealth() * 0.9));
	}

	private void preastHealsMinion(Minion minion) {
		minionManager.healMinion(minion, minion.getMaxHealth() / 10);
	}

	private void performGathererTask(GathererTask townTask, Minion minion) {
		int levelOfBuilding = townTask.getBuildingAssignment().getLevel();
		TownResource res = townTask.getBuildingAssignment().getProducedResource();
		townManager.getTown().getStorage().addResource(res,
				(long) (levelOfBuilding * minion.getSkillValue(Skill.GATHERING)));
		townTask.setDuration(TownManagerConstants.ENDLESS_DURATION);

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
