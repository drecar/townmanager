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
		Storage lager = townManager.getTown().getStorage();
		
		calculateBasics(lager);
		
		//Taks ausführen
		taskManager.performTick();
	}

	private void calculateBasics(Storage lager) {
		lager.addResource(TownResource.FOOD, 10L);
		lager.addResource(TownResource.WOOD, 10L);
		lager.addResource(TownResource.STONE, 10L);
	}

}
