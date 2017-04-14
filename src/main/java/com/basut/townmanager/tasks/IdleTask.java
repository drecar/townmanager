package com.basut.townmanager.tasks;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.ToString;

@ToString
@Entity
@DiscriminatorValue("I")
public class IdleTask extends TownTask {
	
	@Override
	public String getDescription() {
		return "idle";
	}

}
