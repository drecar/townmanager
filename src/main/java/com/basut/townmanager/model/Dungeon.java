package com.basut.townmanager.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.basut.townmanager.utility.enums.TownResource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Dungeon {
	@Id
	@GeneratedValue
	private Long id;
	private int encounters;
	private String name;
	private int difficulty;
	private int duration;
	@ElementCollection(fetch = FetchType.EAGER)
	@Builder.Default private List<TownResource> collectables = new ArrayList<>();
}
