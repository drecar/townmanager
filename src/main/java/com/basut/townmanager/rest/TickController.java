package com.basut.townmanager.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TickController {
	
	@RequestMapping("/")
	public String index() {
		return "Greeting from Townmanager"; 
	}

}
