package com.basut.townmanager.model.buildings;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.basut.townmanager.model.Building;
import com.basut.townmanager.utility.enums.BuildingName;
import com.basut.townmanager.utility.enums.BuildingType;

import lombok.ToString;

@ToString
@Entity
@DiscriminatorValue("Park")
public class Park extends Building {

	public Park() {
		this.name = BuildingName.PARK;
	}
	
	@Override
	public BuildingType getType() {
		return BuildingType.IDLE;
	}
}
