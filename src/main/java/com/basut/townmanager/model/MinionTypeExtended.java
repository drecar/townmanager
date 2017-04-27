package com.basut.townmanager.model;

import com.basut.townmanager.utility.enums.MinionType;
import com.basut.townmanager.utility.enums.Race;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MinionTypeExtended {
	private MinionType name;
	private Race race;
	private short levelUpFactor = 200;
	private MinionTypeAttributes baseAttributes = MinionTypeAttributes.builder().build();
	private MinionTypeAttributes maxLevelUpAttributes = MinionTypeAttributes.builder().build();
}
