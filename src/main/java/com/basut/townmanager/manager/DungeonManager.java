package com.basut.townmanager.manager;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.basut.townmanager.model.Dungeon;
import com.basut.townmanager.repo.IDungeonRepository;

@Component
public class DungeonManager {

	@Autowired
	private IDungeonRepository dungeonRepository;

	public List<Dungeon> getDungeons() {
		return dungeonRepository.findAll();
	}

	public Dungeon getDungeon(Long dungeonId) {
		return dungeonRepository.findOne(dungeonId);	
	}
	public List<Dungeon> getDungeons(Long... longs) {
		return dungeonRepository.findAll(Arrays.asList(longs));
	}

}
