package com.basut.townmanager.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Minion {

	private String name;
	private int strength;
	private TownTask task;
}
