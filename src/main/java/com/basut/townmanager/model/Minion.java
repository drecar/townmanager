package com.basut.townmanager.model;

import cam.basut.townmanager.tasks.IdleTask;
import cam.basut.townmanager.tasks.TownTask;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Minion {

	private String name;
	private int strength;
	private TownTask task = new IdleTask();
}
