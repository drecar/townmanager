package cam.basut.townmanager.tasks;

import com.basut.townmanager.model.BuildingCosts;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GathererResult extends TownResult {

	private BuildingCosts ressources = new BuildingCosts();
}
