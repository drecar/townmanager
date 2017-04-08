package cam.basut.townmanager.tasks;

import com.basut.townmanager.model.Minion;

import lombok.ToString;

@ToString
public class IdleTask extends TownTask {
	
	@Override
	public void performTownTask(Minion minion) {
		// nothing because minion is idle
	}
	
}
