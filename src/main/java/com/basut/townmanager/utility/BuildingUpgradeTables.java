package com.basut.townmanager.utility;

import java.util.HashMap;
import java.util.Map;

import com.basut.townmanager.utility.enums.BuildingName;
import com.basut.townmanager.utility.enums.Resources;
import com.basut.townmanager.utility.enums.UpgradeLevel;

public class BuildingUpgradeTables {

	public static Map<BuildingName, Map<UpgradeLevel, Map<Resources,Long>>> upgradeTables= new HashMap<>();
}
