package com.basut.townmanager.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class BuildingCosts {
	
	@Id
	@GeneratedValue
	Long id;
	
	private int wood;
	private int stone;
	private int food;

	public void addBuildingCosts(BuildingCosts ressources) {
		this.wood += ressources.getWood();
		this.stone += ressources.getStone();
		this.food += ressources.getFood();
	}
}
