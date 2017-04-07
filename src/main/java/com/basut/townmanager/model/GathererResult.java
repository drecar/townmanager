package com.basut.townmanager.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GathererResult extends TownResult {

	private BuildingCosts ressources = new BuildingCosts();
}
