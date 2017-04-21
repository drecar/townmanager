package com.basut.townmanager.model;

import java.util.HashMap;
import java.util.Map;

import com.basut.townmanager.utility.enums.AttackType;
import com.basut.townmanager.utility.enums.Skill;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class MinionTypeAttributes {

	@Builder.Default
	private int maxHealth = 100;
	@Builder.Default
	private int maxMana = 20;
	@Builder.Default
	private Map<AttackType, Integer> attackElements = new HashMap<>();
	@Builder.Default
	private Map<AttackType, Integer> defenceElements = new HashMap<>();
	@Builder.Default
	private Map<Skill, Integer> skills = new HashMap<>();

}
