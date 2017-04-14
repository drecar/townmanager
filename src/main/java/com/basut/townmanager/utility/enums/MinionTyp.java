package com.basut.townmanager.utility.enums;

import lombok.Getter;

@Getter
public enum MinionTyp {
	EISFALKE(Race.BIRD), MEDUSA(Race.BEAST), SORCERER(Race.HUMAN);
	private Race race;
	
	private MinionTyp(Race race) {
		this.race = race;
	}
}
