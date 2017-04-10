package com.basut.townmanager.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.MapKeyEnumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Entity
@Inheritance
@DiscriminatorColumn(name = "building_type")
@Table(name = "building")
public abstract class Building {

	@Id
	@GeneratedValue
	Long id;
	@Column
	private int zustand;
	@Column
	protected String name;
	@Column
	protected UpgradeLevel level = UpgradeLevel.NOT_BUILT;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@MapKeyEnumerated
	protected Map<UpgradeLevel, BuildingCosts> upgradeTable = new HashMap<>();
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	protected Set<Minion> workers = new HashSet<>();

	public abstract boolean upgrade();
	public abstract BuildingType getType();

	public UpgradeLevel getUpgradeLevel() {
		return level;
	}

	public BuildingCosts getUpgradeCosts(UpgradeLevel level) {
		if (upgradeTable.containsKey(level)) {
			return upgradeTable.get(level);
		}

		return new BuildingCosts();

	}

	public BuildingCosts calculateOutput(Minion minion) {
		return new BuildingCosts();
	}

	public Set<Minion> getWorkers() {
		return workers;
	}

}
