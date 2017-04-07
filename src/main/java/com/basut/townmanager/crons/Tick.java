package com.basut.townmanager.crons;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.basut.townmanager.manager.TownManager;

@Component
public class Tick {

	@Autowired
	private TownManager townManager;
	
	@Scheduled(fixedDelay=5000)
	public void tick() {
		townManager.calculateTurn();
	}
}
