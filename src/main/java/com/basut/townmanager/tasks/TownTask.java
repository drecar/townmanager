package com.basut.townmanager.tasks;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.basut.townmanager.model.Minion;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Inheritance
@DiscriminatorColumn(name="TOWN_TASK_TYPE")
@Table(name="TOWN_TASK")
public abstract class TownTask {

	@Id
	@GeneratedValue
	Long id;
	@Column(name="isFinished")
	protected boolean isFinished = false;
	@Column(name = "duration")
	protected int duration = 4;
	@Transient
	protected TownResult townResult;
	
	@Transient
	protected Minion minion;
	
}
