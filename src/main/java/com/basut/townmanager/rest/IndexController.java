package com.basut.townmanager.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.basut.townmanager.manager.TownManager;

@Controller
public class IndexController {
	
	@Autowired
	private TownManager townManager;
	
	@Value("${welcome.message:test}")
	private String message = "Hello World";
	
	@RequestMapping("/")
	public String welcome(Map<String, Object> model) {
		model.put("message", this.message);
		model.put("town", townManager.getTown());
		return "index";
	}
	
	@RequestMapping("/dungeons")
	public String dungeons(Map<String, Object> model) {
		return "redirect:";
	}
	
	@RequestMapping("/workers")
	public String worker(Map<String, Object> model) {
		model.put("message", this.message);
		model.put("town", townManager.getTown());
		return "workers";
	}
}
