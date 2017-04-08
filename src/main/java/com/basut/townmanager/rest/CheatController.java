package com.basut.townmanager.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.basut.townmanager.manager.TownManager;
import com.basut.townmanager.model.Building;
import com.basut.townmanager.model.Minion;
import com.basut.townmanager.model.Town;
import com.basut.townmanager.model.buildings.FireDepartment;
import com.basut.townmanager.model.buildings.HuntingHut;
import com.basut.townmanager.model.buildings.LumberjacksHut;
import com.basut.townmanager.model.buildings.StonemaconHut;

import cam.basut.townmanager.tasks.GathererTask;

@Controller
@RequestMapping("/cheat")
public class CheatController {

	@Autowired
	private TownManager townManager;

	@RequestMapping
	public String cheatBasic() {
		cheatBasisBuildings();

		cheatWorker();

		return "redirect:";
	}

	private void cheatWorker() {
		Town town = townManager.getTown();

		Optional<Building> huntingHut = town.getBuildings().stream()
				.filter(building -> (building instanceof HuntingHut)).findFirst();
		if (huntingHut.isPresent()) {
			Minion hunter = new Minion();
			hunter.setName("hunter");
			town.getWorkers().add(hunter);
			GathererTask gathererTask = GathererTask.builder().buildingAssignment(huntingHut.get()).build();
			hunter.setTask(gathererTask);
		}
		
		Minion workless = new Minion();
		workless.setName("Workless");
		town.getWorkers().add(workless);
	}

	private void cheatBasisBuildings() {
		Town town = townManager.getTown();

		HuntingHut huntingHut = new HuntingHut();
		huntingHut.upgrade();

		StonemaconHut stonemaconHut = new StonemaconHut();
		stonemaconHut.upgrade();
		stonemaconHut.upgrade();

		LumberjacksHut lumberjackyHut = new LumberjacksHut();
		lumberjackyHut.upgrade();
		lumberjackyHut.upgrade();
		lumberjackyHut.upgrade();

		town.getBuildings().add(stonemaconHut);
		town.getBuildings().add(lumberjackyHut);
		town.getBuildings().add(huntingHut);
		town.getBuildings().add(new FireDepartment());
	}
}
