package com.basut.townmanager.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BuildingManager {

	@Autowired
	TownManager townManager; 
	

}
