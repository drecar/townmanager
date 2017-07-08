package com.basut.townmanager.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.basut.townmanager.model.Building;
import com.basut.townmanager.model.Minion;
import com.basut.townmanager.model.Town;
import com.basut.townmanager.model.User;
import com.basut.townmanager.model.buildings.GathererBuilding;
import com.basut.townmanager.model.buildings.Townhall;
import com.basut.townmanager.repo.TownRepository;
import com.basut.townmanager.service.UserService;
import com.basut.townmanager.tasks.GathererTask;
import com.basut.townmanager.tasks.IdleTask;
import com.basut.townmanager.tasks.TownTask;
import com.basut.townmanager.tasks.WorkerTask;
import com.basut.townmanager.utility.TownManagerConstants;
import com.basut.townmanager.utility.enums.AttackType;
import com.basut.townmanager.utility.enums.BuildingName;
import com.basut.townmanager.utility.enums.BuildingType;
import com.basut.townmanager.utility.enums.MinionType;
import com.basut.townmanager.utility.enums.Profession;
import com.basut.townmanager.utility.enums.Race;
import com.basut.townmanager.utility.enums.Skill;
import com.basut.townmanager.utility.enums.TownResource;
import com.google.common.collect.Lists;

import lombok.Getter;

@Component
@Getter
public class TownManager {
	private Town town;
	private static final Logger log = LoggerFactory.getLogger(TownManager.class);

	@Autowired
	MinionManager minionManager;

	@Autowired
	BuildingManager buildingManager;

	@Autowired
	SetupManager setupManager;

	@Autowired
	TownRepository townRepository;
	
	@Autowired
	private UserService userService;
	
	public TownManager() {
	}

	@PostConstruct
	public void init() {
		List<Town> towns = townRepository.findAll();
		if(towns.isEmpty()) {
			town = new Town();
		} else {
			town = towns.get(0);
		}
	}

	public void addWorkerToBuilding(Building building, Minion worker) {
//		building.getWorkers().add(worker);
	}

	private boolean checkBuildingCosts(Map<TownResource, Long> buildingCosts) {
		long count = buildingCosts.keySet().stream()
				.filter(key -> town.getStorage().getResource(key) < buildingCosts.get(key)).count();
		return count < 1;
	}

	public Town getTown() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth == null) {
			return this.town;
		}
		
		//Neuer User bekommt Stadt
		User user = userService.findUserByEmail(auth.getName());
		if(user.getTown() == null) {
			Town newTown = new Town();
			
			
			newTown.setName(user.getName());
			
						
			
			
			Building townhall = new Townhall();
			townhall.setName(BuildingName.TOWNHALL);
			townhall.upgrade();
			newTown.getBuildings().add(townhall);
			
			HashMap<Skill, Integer> basicSkill = new HashMap<>();
			basicSkill.put(Skill.STRENGTH, 5);
			basicSkill.put(Skill.GATHERING, 1);
			
			HashMap<AttackType, Integer> starterAttackElement = new HashMap<>();
			starterAttackElement.put(AttackType.PHYSICAL, 25);
			
			HashMap<AttackType, Integer> starterDefenceElement = new HashMap<>();
			starterDefenceElement.put(AttackType.PHYSICAL, 100);
			starterDefenceElement.put(AttackType.MAGIC, -10);
			
			Minion starter = Minion.builder().name("Worker").minionType(MinionType.MEDUSA).profession(Profession.GATHERER).race(Race.HUMAN)
					.skills(basicSkill).attackElements(starterAttackElement).defenceElements(starterDefenceElement).build();
			newTown.getMinions().add(starter);
			
			newTown.getStorage().addResource(TownResource.FOOD, 1000L);
			newTown.getStorage().addResource(TownResource.WOOD, 1000L);
			newTown.getStorage().addResource(TownResource.STONE, 1000L);
			
			user.setTown(newTown);
			userService.saveUser(user);

			
		}
		return user.getTown();
	}

	/**
	 * Send a minion to work in a building.
	 * 
	 * @param minion
	 * @param building
	 */
	public void sendMinion(Minion minion, Building building) {
		TownTask task = new IdleTask();
		switch (building.getType()) {
		case GATHERER:
			task = GathererTask.builder().buildingAssignment((GathererBuilding) building).build();
			break;
		case REPAIR:
			task = WorkerTask.builder().buildingAssignment(building).build();
		default:
			break;
		}
		minion.setTask(task);
	}

	public boolean upgradeBuilding(long id) {
		boolean result = false;
		Optional<Building> buildingOpt = town.getBuildings().stream().filter(building -> building.getId().equals(id))
				.findFirst();
		if (buildingOpt.isPresent()) {
			Building building = buildingOpt.get();
			if(building.getLevel() == TownManagerConstants.MAX_BUILDING_LEVEL) {
				log.debug("Building ist bereits auf der höchsten Stufe");
			} else {
				Map<TownResource, Long> buildingCosts = buildingManager.getBuildingCosts(building.getName(),
						building.getLevel());
				if (!checkBuildingCosts(buildingCosts)) {
					log.info("Nicht genügend Resourcen um {} upzugraden.", building.getName().getBuildingName());
				} else {
					buildingCosts.keySet().stream()
							.forEach(key -> town.getStorage().removeResource(key, buildingCosts.get(key).longValue()));
					building.upgrade();
					result = true;
					log.info("Upgraded Buidling with id {}.", id);
				}
			}
		}
		return result;
	}
	
	public List<Building> getBuildingsToWorkAt() {
		List<BuildingType> buildingTypesToWorkAt = Lists.newArrayList(BuildingType.REPAIR,BuildingType.GATHERER);
		return town.getBuildings().stream().filter(building -> buildingTypesToWorkAt.contains(building.getType())).collect(Collectors.toList());
	}

	public void saveTown(Town town) {
		townRepository.save(town);
	}
	
}