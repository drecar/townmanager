package com.basut.townmanager.tasks;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.basut.townmanager.model.buildings.GathererBuilding;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Entity
@DiscriminatorValue("G")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class GathererTask extends TownTask {
	
	@ManyToOne
	private GathererBuilding buildingAssignment;
}
