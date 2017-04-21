package com.basut.townmanager.utility.enums;

import lombok.Getter;

@Getter
public enum MinionType {
	EISFALKE(Race.BIRD), MEDUSA(Race.BEAST), SORCERER(Race.HUMAN, 150);

	private Race race;
	private short levelUpFactor = 200;

	private MinionType(Race race) {
		this.race = race;
	}

	private MinionType(Race race, int levelUpFactor) {
		this.race = race;
		this.levelUpFactor = (short) levelUpFactor;
	}
}
