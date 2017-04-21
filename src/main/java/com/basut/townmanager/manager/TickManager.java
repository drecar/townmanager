package com.basut.townmanager.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.basut.townmanager.model.buildings.Storage;
import com.basut.townmanager.utility.enums.TownResource;

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
		
		calculateBasics();
		
		// Execute Tasks
		taskManager.performTick();
		
		//destroy buildings
		buildingManager.decayBuildings();
		
		// let the minions age
		minionManager.letMionionsAge();
	}

	private void calculateBasics() {
		Storage lager = townManager.getTown().getStorage();
		lager.addResource(TownResource.FOOD, 10L);
		lager.addResource(TownResource.WOOD, 10L);
		lager.addResource(TownResource.STONE, 10L);
	}

}
