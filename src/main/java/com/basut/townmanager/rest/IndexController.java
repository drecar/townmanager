package com.basut.townmanager.rest;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.basut.townmanager.form.SendToDungeon;
import com.basut.townmanager.form.SendWorker;
import com.basut.townmanager.manager.DungeonManager;
import com.basut.townmanager.manager.MinionManager;
import com.basut.townmanager.manager.TaskManager;
import com.basut.townmanager.manager.TownManager;
import com.basut.townmanager.model.Building;
import com.basut.townmanager.model.Dungeon;
import com.basut.townmanager.model.Minion;

@Controller
public class IndexController {

	private static final Logger log = LoggerFactory.getLogger(IndexController.class);
	@Autowired
	private TownManager townManager;

	@Value("${welcome.message:test}")
	private String message = "Hello World";

	@Autowired
	private DungeonManager dungeonManager;

	@Autowired
	private MinionManager minionManager;

	@Autowired
	private TaskManager taskManager;

	@RequestMapping("/")
	public String welcome(Map<String, Object> model) {
		model.put("message", this.message);
		model.put("town", townManager.getTown());
		return "index";
	}

	@RequestMapping("/dungeons")
	public String dungeons(Map<String, Object> model) {
		model.put("dungeons", dungeonManager.getDungeons());
		model.put("idleMinions", minionManager.getIdleMinions());
		model.put("sendToDungeon", new SendToDungeon());
		return "dungeons";
	}

	@RequestMapping("/workers")
	public String worker(Map<String, Object> model) {
		model.put("idleMinions", minionManager.getIdleMinions());
		model.put("town", townManager.getTown());
		model.put("sendWorker", new SendWorker());
		return "workers";
	}

	@RequestMapping(value = "/workers/sendWorker", method = RequestMethod.POST)
	public String addNewPost(@Valid SendWorker sendWorker, BindingResult bindingResult, Model model) {
		Optional<Minion> minionOpt = townManager.getTown().getWorkers().stream()
				.filter(minion -> minion.getId().equals(sendWorker.getIdleMinionId())).findFirst();
		Optional<Building> buildingOpt = townManager.getTown().getBuildings().stream()
				.filter(building -> building.getId().equals((sendWorker.getBuildingId()))).findFirst();
		if (minionOpt.isPresent() && buildingOpt.isPresent()) {
			townManager.sendWorker(minionOpt.get(), buildingOpt.get());
		}
		return "redirect:/workers";
	}

	@RequestMapping(value = "/workers/sendToDungeon", method = RequestMethod.POST)
	public String addNewPost(@Valid SendToDungeon sendToDungeon, BindingResult bindingResult, Model model) {
		Long dungeonId = sendToDungeon.getDungeonId();
		Dungeon dungeon = dungeonManager.getDungeon(dungeonId);
		List<Long> minionIdsendToDungeonList = sendToDungeon.getIdleMinionId();
		List<Minion> minionsToSendToDungeon = minionManager.getMinions(minionIdsendToDungeonList);
		taskManager.createDungeonTask(minionsToSendToDungeon, dungeon);
		

		// minions auf dungeontask schicken
		// dungeontask

		return "redirect:/dungeons";
	}
	
	@RequestMapping(value = "/building/upgrade/{id}")
	public String upgradeBuilding(@PathVariable(value="id") long id, Model model) {
		townManager.upgradeBuilding(id);
		return "redirect:/";
	}
}
