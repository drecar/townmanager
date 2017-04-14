package com.basut.townmanager.tasks;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.basut.townmanager.model.Dungeon;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Entity
@DiscriminatorValue("Dungeon")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class DungeonTask extends TownTask {

	@ManyToOne
	private Dungeon dungeon;

	@Override
	public String getDescription() {
		return dungeon.getName();
	}

}
