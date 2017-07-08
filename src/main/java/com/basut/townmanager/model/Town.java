package com.basut.townmanager.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.basut.townmanager.model.buildings.Storage;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
public class Town {
	@Id
	@GeneratedValue
	Long id;
	
	private String name = "Utopia";
	@OneToOne(cascade=CascadeType.ALL)
	private Storage storage = new Storage();
	@OneToMany(cascade=CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Building> buildings= new ArrayList<>();
	@OneToMany(cascade=CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Minion> minions = new ArrayList<>();
	@OneToOne(cascade=CascadeType.ALL)
	private User user;
}