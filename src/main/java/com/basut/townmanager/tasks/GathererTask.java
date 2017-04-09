package com.basut.townmanager.tasks;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.basut.townmanager.model.Building;
import com.basut.townmanager.model.BuildingCosts;
import com.basut.townmanager.model.Minion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Entity
@DiscriminatorValue("G")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GathererTask extends TownTask {

	@ManyToOne
	private Building buildingAssignment;

	@Override
	public void performTownTask(Minion minion) {
		duration--;
		if (duration == 0) {
			GathererResult result = new GathererResult();
			BuildingCosts ressources = buildingAssignment.calculateOutput(minion);
			result.setRessources(ressources);

			townResult = result;
			isFinished = true;
		}
	}
}
