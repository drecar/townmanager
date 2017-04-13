package com.basut.townmanager.rest;

import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.basut.townmanager.form.SendToDungeon;
import com.basut.townmanager.form.SendWorker;
import com.basut.townmanager.manager.DungeonManager;
import com.basut.townmanager.manager.MinionManager;
import com.basut.townmanager.manager.TownManager;
import com.basut.townmanager.model.Building;
import com.basut.townmanager.model.Minion;

@Controller
public class IndexController {

	@Autowired
	private TownManager townManager;

	@Value("${welcome.message:test}")
	private String message = "Hello World";

	@Autowired
	private DungeonManager dungeonManager;

	@Autowired
	private MinionManager minionManager;

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

		// minions und dungeons aus id bestimmen
		// minions auf dungeontask schicken
		// dungeontask

		// Optional<Building> buildingOpt =
		// townManager.getTown().getBuildings().stream()
		// .filter(building ->
		// building.getId().equals((sendToDungeon.getBuildingId()))).findFirst();
		// if (minionOpt.isPresent() && buildingOpt.isPresent()) {
		// townManager.sendWorker(minionOpt.get(), buildingOpt.get());
		// }
		return "redirect:/dungeons";
	}
}
