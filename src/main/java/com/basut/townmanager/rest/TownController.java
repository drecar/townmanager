package com.basut.townmanager.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.basut.townmanager.manager.TownManager;

@RestController
public class TownController {
	
	@Autowired
	private TownManager townManager;
	
	@RequestMapping("/town/storage")
	public String index() {
		return townManager.getTown().getStorage().toString();
	}
}
