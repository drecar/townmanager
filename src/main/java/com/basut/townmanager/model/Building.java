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
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.basut.townmanager.utility.enums.BuildingName;
import com.basut.townmanager.utility.enums.BuildingType;
import com.basut.townmanager.utility.enums.Resources;
import com.basut.townmanager.utility.enums.UpgradeLevel;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Entity
@Inheritance
@DiscriminatorColumn(name = "building_type")
public abstract class Building {

	@Id
	@GeneratedValue
	protected Long id;
	@Column
	protected int zustand;
	@Column
	protected UpgradeLevel level = UpgradeLevel.NOT_BUILT;
	protected BuildingName name;
	
	@Transient
	protected Map<UpgradeLevel, Map<Resources,Long>> upgradeTable = new HashMap<>();
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	protected Set<Minion> workers = new HashSet<>();

	public abstract boolean upgrade();
	public abstract BuildingType getType();

	public UpgradeLevel getUpgradeLevel() {
		return level;
	}

	public Map<Resources,Long> getUpgradeCosts(UpgradeLevel level) {
		if (upgradeTable.containsKey(level)) {
			return upgradeTable.get(level);
		}
		return new HashMap<Resources,Long>();
	}
}
