package com.basut.townmanager.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BuildingCosts {
	private int wood;
	private int stone;
	private int food;

	public void addBuildingCosts(BuildingCosts ressources) {
		this.wood += ressources.getWood();
		this.stone += ressources.getStone();
		this.food += ressources.getFood();
	}
}
