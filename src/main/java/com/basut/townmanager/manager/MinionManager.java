package com.basut.townmanager.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.basut.townmanager.model.Town;
import com.basut.townmanager.repo.MinionRepository;

@Component
public class MinionManager {
	
	@Autowired
	MinionRepository minionRepository;
		
	public void initMonsters(Town town) {
		
	}
	
	
}
