package com.basut.townmanager.crons;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.basut.townmanager.manager.TickManager;

@Component
public class Tick {

	@Autowired
	private TickManager tickManager;

	@Scheduled(fixedDelay = 5000)
	public void tick() {
		tickManager.tick();
	}
}
