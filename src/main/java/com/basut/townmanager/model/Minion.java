package com.basut.townmanager.model;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.basut.townmanager.tasks.IdleTask;
import com.basut.townmanager.tasks.TownTask;
import com.basut.townmanager.utility.enums.AttackType;
import com.basut.townmanager.utility.enums.MinionType;
import com.basut.townmanager.utility.enums.Profession;
import com.basut.townmanager.utility.enums.Race;
import com.basut.townmanager.utility.enums.Skill;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Minion {

	@Id
	@GeneratedValue
	Long id;

	@Column
	private String name;

	@Column
	@Builder.Default
	private int age = 0;

	@Column
	@Builder.Default
	private int level = 1;

	@Column
	@Builder.Default
	private int health = 100;

	@Column
	@Builder.Default
	private int maxHealth = 100;

	@Column
	@Builder.Default
	private MinionType minionType = MinionType.EISFALKE;

	@ElementCollection(fetch = FetchType.EAGER)
	@Builder.Default
	private Map<AttackType, Integer> attackElements = new HashMap<>();

	@ElementCollection(fetch = FetchType.EAGER)
	@Builder.Default
	private Map<AttackType, Integer> defenceElements = new HashMap<>();

	@Column
	private Profession profession;
	
	@Column
	private Race race;

	@Column
	@Builder.Default
	private int exp = 0;

	@ElementCollection(fetch = FetchType.EAGER)
	@Builder.Default
	private Map<Skill, Integer> skills = new HashMap<>();

	@OneToOne(cascade = CascadeType.ALL)
	@Builder.Default
	private TownTask task = new IdleTask();

	public TownTask getTask() {
		if (task == null) {
			return new IdleTask();
		}
		return task;
	}

	public int getSkillValue(Skill skill) {
		Integer skillValue = skills.get(skill);
		if (skillValue == null) {
			return 0;
		}
		return skillValue;
	}

	public double getDefenceValue(AttackType element) {
		Integer defenceElement = defenceElements.get(element);
		if (defenceElement == null) {
			return 1;
		}
		return (100 - defenceElement) / 100.0;
	}
}
