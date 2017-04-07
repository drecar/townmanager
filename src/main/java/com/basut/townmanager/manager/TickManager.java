package com.basut.townmanager.manager;

import org.springframework.beans.factory.annotation.Autowired;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TickManager {

	@Autowired
	private TownManager townManager;
}
