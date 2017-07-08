package com.basut.townmanager.model.buildings;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.basut.townmanager.model.Building;
import com.basut.townmanager.utility.enums.BuildingName;
import com.basut.townmanager.utility.enums.BuildingType;

import lombok.ToString;

@ToString
@Entity
@DiscriminatorValue("Townhall")
public class Townhall extends Building {

	public Townhall() {
		this.name = BuildingName.TOWNHALL;
	}
		@Override
		public BuildingType getType() {
			return BuildingType.TOWN;
}
}