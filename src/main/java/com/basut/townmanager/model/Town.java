package com.basut.townmanager.model;

import java.util.ArrayList;
import java.util.List;

public class Town {
	private String name = "Utopia";
	private Storage storage = new Storage();
	private List<Building> buildings= new ArrayList<>();
	private List<Worker> worker = new ArrayList<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Storage getStorage() {
		return storage;
	}

	@Override
	public String toString() {
		return name;
	}

	public List<Building> getBuildings() {
		return buildings;
	}
	
}