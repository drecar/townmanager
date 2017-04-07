package com.basut.townmanager.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class TownTask {

	protected boolean isFinished;
	protected int duration = 1;
	protected TownResult townResult;
	
	public abstract void performTownTask(Minion minion);
}
