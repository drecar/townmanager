package com.basut.townmanager.utility;

import java.util.HashMap;
import java.util.Map;

import com.basut.townmanager.utility.enums.BuildingName;
import com.basut.townmanager.utility.enums.TownResource;
import com.basut.townmanager.utility.enums.UpgradeLevel;

public class BuildingUpgradeTables {

	public static Map<BuildingName, Map<UpgradeLevel, Map<TownResource,Long>>> upgradeTables= new HashMap<>();
}
