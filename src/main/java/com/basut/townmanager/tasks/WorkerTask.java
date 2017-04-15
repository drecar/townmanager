package com.basut.townmanager.tasks;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.basut.townmanager.model.Building;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Entity
@DiscriminatorValue("Worker")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class WorkerTask extends TownTask{
	@ManyToOne
	private Building buildingAssignment;

	@Override
	public String getDescription() {
		return buildingAssignment.getName().getBuildingName();
	}
	
}
