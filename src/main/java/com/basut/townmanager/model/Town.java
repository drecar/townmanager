package com.basut.townmanager.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Town {
	private String name = "Utopia";
	private Storage storage = new Storage();
	private List<Building> buildings= new ArrayList<>();
	private List<Minion> worker = new ArrayList<>();	
}