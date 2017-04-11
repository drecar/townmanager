package com.basut.townmanager.tasks;

import java.util.HashMap;
import java.util.Map;

import com.basut.townmanager.utility.enums.Resources;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GathererResult extends TownResult {

	private Map<Resources, Long> ressources = new HashMap<>();
	
}
