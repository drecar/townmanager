package com.basut.townmanager.manager;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.basut.townmanager.model.Building;
import com.basut.townmanager.model.Dungeon;
import com.basut.townmanager.model.Minion;
import com.basut.townmanager.model.MinionTypeExtended;
import com.basut.townmanager.model.Town;
import com.basut.townmanager.model.buildings.FireDepartment;
import com.basut.townmanager.model.buildings.GathererBuilding;
import com.basut.townmanager.model.buildings.Park;
import com.basut.townmanager.model.buildings.TownBuilding;
import com.basut.townmanager.repo.BuildingRepository;
import com.basut.townmanager.repo.IDungeonRepository;
import com.basut.townmanager.repo.MinionRepository;
import com.basut.townmanager.repo.TownRepository;
import com.basut.townmanager.tasks.GathererTask;
import com.basut.townmanager.utility.enums.AttackType;
import com.basut.townmanager.utility.enums.BuildingName;
import com.basut.townmanager.utility.enums.MinionType;
import com.basut.townmanager.utility.enums.Profession;
import com.basut.townmanager.utility.enums.Skill;
import com.basut.townmanager.utility.enums.TownResource;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;

@Component
public class SetupManager {

	private static final Logger log = LoggerFactory.getLogger(SetupManager.class);

	@Autowired
	private TownManager townManager;

	@Autowired
	BuildingRepository buildingRepository;

	@Autowired
	MinionRepository minionRepository;

	@Autowired
	TownRepository townRepository;

	@Autowired
	IDungeonRepository dungeonRepository;

	@Autowired
	MinionManager minionManager;

	@PostConstruct
	public void initDatabase() {
		createMinionType();
		createMonsters();
	}

	private void createMinionType() {
		File file = new File("src/main/resources/data/MinionTypes");
		if (file.exists()) {
			List<File> list = Arrays.asList(file.listFiles());
			list.forEach(type -> handle(type));
		} else {
			log.error("Datei existiert nicht");
		}

	}

	private void handle(File type) {
		log.info("Dateiname: {}", type.getName());
		ObjectMapper mapper = new ObjectMapper();
		try {
			MinionTypeExtended minionType = mapper.readValue(type, MinionTypeExtended.class);
			minionManager.getSpecies().add(minionType);

		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void createMonsters() {
		cheatBasicBuildings();
		cheatDungeons();
		cheatWorker();

		List<Minion> minions = minionRepository.findAll();
		townManager.getTown().setMinions(minions);
	}

	public void cheatWorker() {
		Town town = townManager.getTown();

		HashMap<Skill, Integer> basicSkill = new HashMap<>();
		basicSkill.put(Skill.CURSE, 2);
		basicSkill.put(Skill.STRENGTH, 5);
		basicSkill.put(Skill.GATHERING, 1);

		HashMap<AttackType, Integer> merlinAttackElement = new HashMap<>();
		merlinAttackElement.put(AttackType.FIRE, 50);
		merlinAttackElement.put(AttackType.ELECTRICAL, 40);
		merlinAttackElement.put(AttackType.MAGIC, 10);

		HashMap<AttackType, Integer> medusaAttackElement = new HashMap<>();
		medusaAttackElement.put(AttackType.MAGIC, 60);
		medusaAttackElement.put(AttackType.PHYSICAL, 20);

		HashMap<AttackType, Integer> cassioAttackElement = new HashMap<>();
		cassioAttackElement.put(AttackType.MAGIC, 50);
		cassioAttackElement.put(AttackType.POISON, 50);

		HashMap<AttackType, Integer> merlinDefenceElement = new HashMap<>();
		merlinDefenceElement.put(AttackType.DARK, -40);
		merlinDefenceElement.put(AttackType.MAGIC, 100);

		HashMap<AttackType, Integer> medusaDefenceElement = new HashMap<>();
		medusaDefenceElement.put(AttackType.MAGIC, 60);
		medusaDefenceElement.put(AttackType.PHYSICAL, 20);

		HashMap<AttackType, Integer> cassioDefenceElement = new HashMap<>();
		cassioDefenceElement.put(AttackType.MAGIC, 50);
		cassioDefenceElement.put(AttackType.POISON, 50);

		Optional<Building> huntingHut = town.getBuildings().stream()
				.filter(building -> (building.getName().equals(BuildingName.HUNTING_HUT))).findFirst();
		if (huntingHut.isPresent()) {
			Minion hunter = Minion.builder().name("hunter").build();
			town.getMinions().add(hunter);
			GathererTask gathererTask = GathererTask.builder().buildingAssignment((GathererBuilding) huntingHut.get())
					.build();
			hunter.setTask(gathererTask);
		}
		Minion workless = Minion.builder().name("Workless").build();
		town.getMinions().add(workless);

		Minion cassiopeia = Minion.builder().name("Cassiopeia").minionType(MinionType.MEDUSA)
				.profession(Profession.MAGE).skills(basicSkill).attackElements(cassioAttackElement)
				.defenceElements(cassioDefenceElement).build();

		Minion merlin = Minion.builder().name("Merlin").minionType(MinionType.SORCERER).profession(Profession.MAGE)
				.skills(basicSkill).attackElements(merlinAttackElement).defenceElements(merlinDefenceElement).build();

		Minion medusa = Minion.builder().name("Medusa").minionType(MinionType.MEDUSA).profession(Profession.HUNTER)
				.skills(basicSkill).attackElements(medusaAttackElement).defenceElements(medusaDefenceElement).build();

		town.getMinions().add(cassiopeia);
		town.getMinions().add(merlin);
		town.getMinions().add(medusa);
		minionRepository.save(townManager.getTown().getMinions());
	}

	public void cheatBasicBuildings() {
		Town town = townManager.getTown();

		GathererBuilding huntingHut = new GathererBuilding();
		huntingHut.setProducedResource(TownResource.FOOD);
		huntingHut.setName(BuildingName.HUNTING_HUT);

		GathererBuilding stonemaconHut = new GathererBuilding();
		stonemaconHut.setProducedResource(TownResource.STONE);
		stonemaconHut.setName(BuildingName.STONEMACONS_HUT);

		GathererBuilding lumberjackyHut = new GathererBuilding();
		lumberjackyHut.setProducedResource(TownResource.WOOD);
		lumberjackyHut.setName(BuildingName.LUMBERJACKS_HUT);

		huntingHut.upgrade();

		stonemaconHut.upgrade();
		stonemaconHut.upgrade();

		lumberjackyHut.upgrade();
		lumberjackyHut.upgrade();
		lumberjackyHut.upgrade();

		Park park = new Park();

		FireDepartment fireDepartment = new FireDepartment();
		fireDepartment.upgrade();
		fireDepartment.upgrade();

		TownBuilding portal = new TownBuilding();
		portal.setName(BuildingName.PORTAL);
		portal.upgrade();

		town.getBuildings().add(park);
		town.getBuildings().add(stonemaconHut);
		town.getBuildings().add(lumberjackyHut);
		town.getBuildings().add(huntingHut);
		town.getBuildings().add(fireDepartment);
		town.getBuildings().add(portal);

		buildingRepository.save(town.getBuildings());
	}

	public void cheatDungeons() {

		Dungeon field = new Dungeon();
		field.setName("field");
		field.setDuration(1);
		field.setDifficulty(1);
		field.setEncounters(40);
		field.setCollectables(Lists.newArrayList(TownResource.RUBY, TownResource.FOOD));
		field.setCreatureElements(Lists.newArrayList(AttackType.PHYSICAL, AttackType.POISON, AttackType.WATER));

		Dungeon cave = Dungeon.builder().name("cave").duration(20).difficulty(2).encounters(5)
				.collectables(Lists.newArrayList(TownResource.COINS, TownResource.STONE))
				.creatureElements(Lists.newArrayList(AttackType.PHYSICAL, AttackType.DARK, AttackType.BLOOD)).build();

		Dungeon forest = Dungeon.builder().name("forest").duration(15).difficulty(3).encounters(5)
				.collectables(Lists.newArrayList(TownResource.FOOD, TownResource.WOOD))
				.creatureElements(Lists.newArrayList(AttackType.LIGHT, AttackType.PHYSICAL, AttackType.POISON)).build();

		dungeonRepository.save(Lists.newArrayList(field, cave, forest));
	}
}
