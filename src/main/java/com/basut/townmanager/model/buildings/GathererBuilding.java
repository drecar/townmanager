package com.basut.townmanager.model.buildings;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.basut.townmanager.model.Building;
import com.basut.townmanager.utility.enums.BuildingType;
import com.basut.townmanager.utility.enums.Resources;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Entity
@DiscriminatorValue("Gathering")
@NoArgsConstructor
public class GathererBuilding extends Building{
	private Resources producedResource;
	
	@Override
	public BuildingType getType() {
		return BuildingType.GATHERER;
	}

	@Override
	public boolean upgrade() {
		this.level = level.nextLevel();
		return true;
	}
}
