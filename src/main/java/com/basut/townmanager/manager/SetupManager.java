package com.basut.townmanager.manager;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.basut.townmanager.model.Building;
import com.basut.townmanager.model.Minion;
import com.basut.townmanager.model.Town;
import com.basut.townmanager.model.buildings.FireDepartment;
import com.basut.townmanager.model.buildings.HuntingHut;
import com.basut.townmanager.model.buildings.LumberjacksHut;
import com.basut.townmanager.model.buildings.StonemaconHut;
import com.basut.townmanager.repo.BuildingRepository;
import com.basut.townmanager.repo.MinionRepository;
import com.basut.townmanager.repo.TownRepository;
import com.basut.townmanager.tasks.GathererTask;

@Component
public class SetupManager {
	@Autowired
	private TownManager townManager;
	
	@Autowired
	BuildingRepository buildingRepository;
	
	@Autowired
	MinionRepository minionRepository;
	
	@Autowired
	TownRepository townRepository;
	
	@PostConstruct
	public void initDatabase() {
		createMonsters();
	}
	
	private void createMonsters() {
		cheatBasicBuildings();
		
		cheatWorker();
		
		List<Minion> minions = minionRepository.findAll();
		townManager.getTown().setWorkers(minions);
	}
	
	public void cheatWorker() {
		Town town = townManager.getTown();

		Optional<Building> huntingHut = town.getBuildings().stream()
				.filter(building -> (building instanceof HuntingHut)).findFirst();
		if (huntingHut.isPresent()) {
			Minion hunter = new Minion();
			hunter.setName("hunter");
			town.getWorkers().add(hunter);
			GathererTask gathererTask = GathererTask.builder().buildingAssignment(huntingHut.get()).build();
			hunter.setTask(gathererTask);
		}
		
		Minion workless = new Minion();
		workless.setName("Workless");
		town.getWorkers().add(workless);

		minionRepository.save(townManager.getTown().getWorkers());
	}

	public void cheatBasicBuildings() {
		Town town = townManager.getTown();

		HuntingHut huntingHut = new HuntingHut();
		huntingHut.upgrade();

		StonemaconHut stonemaconHut = new StonemaconHut();
		stonemaconHut.upgrade();
		stonemaconHut.upgrade();

		LumberjacksHut lumberjackyHut = new LumberjacksHut();
		lumberjackyHut.upgrade();
		lumberjackyHut.upgrade();
		lumberjackyHut.upgrade();

		town.getBuildings().add(stonemaconHut);
		town.getBuildings().add(lumberjackyHut);
		town.getBuildings().add(huntingHut);
		town.getBuildings().add(new FireDepartment());
		
		buildingRepository.save(town.getBuildings());
	}
}