package com.basut.townmanager.rest;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.basut.townmanager.manager.DungeonManager;
import com.basut.townmanager.manager.TownManager;
import com.basut.townmanager.model.Dungeon;

@Controller
public class IndexController {

	@Autowired
	private TownManager townManager;

	@Value("${welcome.message:test}")
	private String message = "Hello World";

	@Autowired
	private DungeonManager dungeonManager;

	@RequestMapping("/")
	public String welcome(Map<String, Object> model) {
		model.put("message", this.message);
		model.put("town", townManager.getTown());
		return "index";
	}

	@RequestMapping("/dungeons")
	public String dungeons(Map<String, Object> model) {
		List<Dungeon> dungeons2 = dungeonManager.getDungeons();
		model.put("dungeons", dungeonManager.getDungeons());

		return "dungeons";
	}

	@RequestMapping("/workers")
	public String worker(Map<String, Object> model) {
		model.put("message", this.message);
		model.put("town", townManager.getTown());
		return "workers";
	}
}
