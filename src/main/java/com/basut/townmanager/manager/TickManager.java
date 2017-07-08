package com.basut.townmanager.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.basut.townmanager.model.Town;
import com.basut.townmanager.model.User;
import com.basut.townmanager.model.buildings.Storage;
import com.basut.townmanager.service.UserService;
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

	@Autowired
	private UserService userService;
	
	public void tick() {
	
		taskManager.prepareNextTick();
		List<User> findAllUsers = userService.findAllUsers();
		findAllUsers.forEach(user -> performTickForUser(user));
	}
	
	private void performTickForUser(User user) {
		calculateBasics(user.getTown());
		
		// Execute Tasks
		taskManager.performTick(user.getTown());
		
		//destroy buildings
		buildingManager.decayBuildings(user.getTown());
		
		// let the minions age
		minionManager.letMinionsAge(user.getTown().getMinions());
		
		// save town
		townManager.saveTown(user.getTown());
	}

	private void calculateBasics(Town town) {
		Storage lager = town.getStorage();
		lager.addResource(TownResource.FOOD, 10L);
		lager.addResource(TownResource.WOOD, 10L);
		lager.addResource(TownResource.STONE, 10L);
	}

}
