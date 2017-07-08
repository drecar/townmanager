package com.basut.townmanager.model.buildings;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.basut.townmanager.model.Building;
import com.basut.townmanager.utility.enums.BuildingName;
import com.basut.townmanager.utility.enums.BuildingType;
import com.basut.townmanager.utility.enums.TownResource;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class GathererBuilding extends Building{
	private TownResource producedResource;
	
	@Override
	public BuildingType getType() {
		return BuildingType.GATHERER;
	}
	
	public GathererBuilding(BuildingName bn, TownResource tr) {
		this.name = bn;
		this.producedResource = tr;
	}
}
