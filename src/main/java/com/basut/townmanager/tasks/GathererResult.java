package com.basut.townmanager.tasks;

import java.util.HashMap;
import java.util.Map;

import com.basut.townmanager.utility.enums.TownResource;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GathererResult extends TownResult {

	private Map<TownResource, Long> ressources = new HashMap<>();
	
}
