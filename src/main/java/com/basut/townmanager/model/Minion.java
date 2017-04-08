package com.basut.townmanager.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import cam.basut.townmanager.tasks.IdleTask;
import cam.basut.townmanager.tasks.TownTask;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Minion")
public class Minion {

	@Id
	@GeneratedValue
	Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "strength")
	private int strength;
	
	@Transient
	private TownTask task = new IdleTask();
}
