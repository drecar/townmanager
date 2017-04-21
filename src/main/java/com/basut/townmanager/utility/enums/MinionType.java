package com.basut.townmanager.utility.enums;

import com.basut.townmanager.model.MinionTypeAttributes;

import lombok.Getter;

@Getter
public enum MinionType {
	EISFALKE(Race.BIRD), MEDUSA(Race.BEAST), SORCERER(Race.HUMAN, 150);

	private Race race;
	private short levelUpFactor = 200;
	private MinionTypeAttributes baseAttributes = MinionTypeAttributes.builder().build();
	private MinionTypeAttributes levelUpAttributes = MinionTypeAttributes.builder().build();

	private MinionType(Race race) {
		this.race = race;
	}

	private MinionType(Race race, int levelUpFactor) {
		this.race = race;
		this.levelUpFactor = (short) levelUpFactor;
	}
}
