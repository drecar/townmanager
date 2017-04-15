package com.basut.townmanager.manager;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.basut.townmanager.model.Minion;
import com.basut.townmanager.model.Town;
import com.basut.townmanager.tasks.GathererTask;
import com.basut.townmanager.tasks.IdleTask;
import com.basut.townmanager.tasks.TownTask;
import com.basut.townmanager.utility.enums.AttackType;

@Component
public class MinionManager {

	private static final Logger log = LoggerFactory.getLogger(MinionManager.class);
	
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

	public List<Minion> getMinions(List<Long> minionIdsendToDungeonList) {
		return townManager.getTown().getWorkers().stream()
				.filter(minion -> minionIdsendToDungeonList.contains(minion.getId())).collect(Collectors.toList());
	}

	public List<Minion> getMinionFromTask(TownTask task) {
		return townManager.getTown().getWorkers().stream().filter(minion -> minion.getTask().equals(task))
				.collect(Collectors.toList());
	}

	public void healMinion(Minion minion, int heal) {
		minion.setHealth(minion.getHealth() + heal);
		if (minion.getHealth() > minion.getMaxHealth()) {
			minion.setHealth(minion.getMaxHealth());
		}
	}
	
	public void dealDirectDamageMinion(Minion minion, int damage) {
		minion.setHealth(minion.getHealth() -damage);
		if (minion.getHealth() <= 0) {
			minion.setHealth(0);
		}
	}

	public void dealDamageToMinion(Minion minion, Map<AttackType, Integer> damageForMinion) {
			damageForMinion.keySet().stream().forEach(key ->{
				int damageOfType = (int) (damageForMinion.get(key)*minion.getDefenceValue(key));
				dealDirectDamageMinion(minion, damageOfType);
				log.info("Minion{} bekam {} {} Schaden. Remaining health: {}",minion.getName(), damageOfType, key, minion.getHealth() );
			});
	}
}
