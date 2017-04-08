package cam.basut.townmanager.tasks;

import com.basut.townmanager.model.Building;
import com.basut.townmanager.model.BuildingCosts;
import com.basut.townmanager.model.Minion;

import lombok.Builder;

@Builder
public class GathererTask extends TownTask {

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
