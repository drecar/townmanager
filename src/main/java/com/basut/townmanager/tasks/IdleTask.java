package com.basut.townmanager.tasks;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.basut.townmanager.model.Minion;

import lombok.ToString;

@ToString
@Entity
@DiscriminatorValue("I")
public class IdleTask extends TownTask {
	
	@Override
	public void performTownTask(Minion minion) {
		// nothing because minion is idle
	}
	
}
