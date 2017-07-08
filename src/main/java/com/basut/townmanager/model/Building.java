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

import com.basut.townmanager.utility.TownManagerConstants;
import com.basut.townmanager.utility.enums.BuildingName;
import com.basut.townmanager.utility.enums.BuildingType;
import com.basut.townmanager.utility.enums.TownResource;

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
	protected int zustand = TownManagerConstants.MAX_BUILDING_CONDITION;
	@Column
	protected int level = 0;
	protected BuildingName name;
	
	@Transient
	protected Map<Integer, Map<TownResource,Long>> upgradeTable = new HashMap<>();
//	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//	protected Set<Minion> workers = new HashSet<>();

	public boolean upgrade() {
		if(level < TownManagerConstants.MAX_BUILDING_LEVEL) {
			this.level +=1 ;
		}
		return true;
	}
	
	public abstract BuildingType getType();

	public Map<TownResource,Long> getUpgradeCosts(int level) {
		if (upgradeTable.containsKey(level)) {
			return upgradeTable.get(level);
		}
		return new HashMap<TownResource,Long>();
	}
}
