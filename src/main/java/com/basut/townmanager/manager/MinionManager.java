package com.basut.townmanager.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.basut.townmanager.model.Minion;
import com.basut.townmanager.model.MinionTypeExtended;
import com.basut.townmanager.model.Town;
import com.basut.townmanager.tasks.DungeonTask;
import com.basut.townmanager.tasks.GathererTask;
import com.basut.townmanager.tasks.IdleTask;
import com.basut.townmanager.tasks.TownTask;
import com.basut.townmanager.utility.TownManagerConstants;
import com.basut.townmanager.utility.enums.AttackType;
import com.basut.townmanager.utility.enums.Skill;

import lombok.Getter;

@Component
public class MinionManager {

	private static final Logger log = LoggerFactory.getLogger(MinionManager.class);

	@Getter
	private List<MinionTypeExtended> species = new ArrayList<>();

	@Autowired
	private TownManager townManager;

	public void resetTask(Minion minion) {
		TownTask oldTask = minion.getTask();
		if (oldTask instanceof GathererTask) {
			GathererTask oldGathererTask = (GathererTask) oldTask;
		}
		minion.setTask(new IdleTask());
	}

	public void distributeExpToMinion(Minion minion, int exp) {

		// man nehme Minion und erhaltene Erfahrung

		// Minion bekommt Erfahrung
		minion.setExp(minion.getExp() + exp);
		int expForNextLevel = getExpForNextLevel(minion);
		// Check Level Up
		if (minion.getExp() >= expForNextLevel) {
			minion.setExp(minion.getExp() - expForNextLevel);
			levelUpMinion(minion);
		}
	}

	public void levelUpMinion(Minion minion) {

		// man nehme Minion
		// erhöhe das Level (wenn nicht max level)
		if (minion.getLevel() < TownManagerConstants.MAX_LEVEL) {
			minion.setLevel(minion.getLevel() + 1);
			log.info("{} reached Level {}", minion.getName(), minion.getLevel());

			// erhöhe Attribute etc
			updateMinion(minion);
		}
	}

	public List<Minion> getAvailableMinions() {
		return townManager.getTown().getMinions().stream().filter(minion -> !(minion.getTask() instanceof DungeonTask))
				.collect(Collectors.toList());
	}

	public List<Minion> getMinions(List<Long> minionIdsendToDungeonList) {
		return townManager.getTown().getMinions().stream()
				.filter(minion -> minionIdsendToDungeonList.contains(minion.getId())).collect(Collectors.toList());
	}

	public List<Minion> getMinionFromTask(TownTask task) {
		return townManager.getTown().getMinions().stream().filter(minion -> minion.getTask().equals(task))
				.collect(Collectors.toList());
	}

	public void healMinion(Minion minion, int heal) {
		minion.setHealth(minion.getHealth() + heal);
		if (minion.getHealth() > minion.getMaxHealth()) {
			minion.setHealth(minion.getMaxHealth());
		}
	}

	public void dealDirectDamageMinion(Minion minion, int damage) {
		minion.setHealth(minion.getHealth() - damage);
		if (minion.getHealth() <= 0) {
			minion.setHealth(0);
		}
	}

	public void dealDamageToMinion(Minion minion, Map<AttackType, Integer> damageForMinion) {
		damageForMinion.keySet().stream().forEach(key -> {
			int damageOfType = (int) (damageForMinion.get(key) * minion.getDefenceValue(key));
			dealDirectDamageMinion(minion, damageOfType);
			log.debug("Minion {} bekam {} {} Schaden. Remaining health: {}", minion.getName(), damageOfType, key,
					minion.getHealth());
		});
	}

	public int getExpForNextLevel(Minion minion) {
		int minionLevel = minion.getLevel();
		Optional<MinionTypeExtended> expMinionType = getMinionTypeExtended(minion);
		int levelUpFactor = TownManagerConstants.DEFAULT_LEVEL_UP_FACTOR;
		if (expMinionType.isPresent()) {
			levelUpFactor = expMinionType.get().getLevelUpFactor();
		}
		int expForNextLevel = (minionLevel ^ 2 + minionLevel) * levelUpFactor / 2;

		return expForNextLevel;
	}

	public void letMinionsAge() {
		townManager.getTown().getMinions().forEach(minion -> minion.setAge(minion.getAge() + 1));

	}

	public Minion updateMinion(Minion minion) {

		Optional<MinionTypeExtended> expMinionType = getMinionTypeExtended(minion);
		if (expMinionType.isPresent()) {
			MinionTypeExtended type = expMinionType.get();
			double maxLevelFactor = minion.getLevel() / (double) TownManagerConstants.MAX_LEVEL;
			int maxHealth = (int) (type.getBaseAttributes().getMaxHealth()
					+ (type.getMaxLevelUpAttributes().getMaxHealth() * maxLevelFactor));
			minion.setMaxHealth(maxHealth);

			Map<AttackType, Integer> totalAttackElements = new HashMap<>();
			addAttackToMap(type.getBaseAttributes().getAttackElements(), 1.0, totalAttackElements);
			addAttackToMap(type.getMaxLevelUpAttributes().getAttackElements(), maxLevelFactor, totalAttackElements);

			minion.setAttackElements(totalAttackElements);

			Map<AttackType, Integer> totalDefenceElements = new HashMap<>();
			addAttackToMap(type.getBaseAttributes().getDefenceElements(), 1.0, totalDefenceElements);
			addAttackToMap(type.getMaxLevelUpAttributes().getDefenceElements(), maxLevelFactor, totalDefenceElements);

			minion.setDefenceElements(totalDefenceElements);

			Map<Skill, Integer> totalSkills = new HashMap<>();
			addSkillToMap(type.getBaseAttributes().getSkills(), 1.0, totalSkills);
			addSkillToMap(type.getMaxLevelUpAttributes().getSkills(), maxLevelFactor, totalSkills);

			minion.setSkills(totalSkills);
		}
		return minion;
	}

	private Map<Skill, Integer> addSkillToMap(Map<Skill, Integer> skills, double maxLevelFactor,
			Map<Skill, Integer> totalSkills) {

		skills.keySet().forEach(key -> {
			int skill = 0;
			if (totalSkills.keySet().contains(key)) {
				skill = totalSkills.get(key);
			}
			int value = (int) (maxLevelFactor * (skills.get(key)) + skill);
			totalSkills.put(key, value);

		});
		return totalSkills;

	}

	private Map<AttackType, Integer> addAttackToMap(Map<AttackType, Integer> newAttackElement, double maxLevelFactor,
			Map<AttackType, Integer> totalAttackElements) {
		newAttackElement.keySet().forEach(key -> {
			int element = 0;
			if (totalAttackElements.keySet().contains(key)) {
				element = totalAttackElements.get(key);
				System.out.println("Get Base Attack value " + element);
			}
			int value = (int) (maxLevelFactor * (newAttackElement.get(key)) + element);
			totalAttackElements.put(key, value);
			System.out.println("Setting " + key + " to " + value);
		});
		return totalAttackElements;
	}

	private Optional<MinionTypeExtended> getMinionTypeExtended(Minion minion) {
		Optional<MinionTypeExtended> expMinionType = species.stream()
				.filter(minionTypeExp -> minionTypeExp.getName().equals(minion.getMinionType())).findFirst();
		return expMinionType;
	}
}
