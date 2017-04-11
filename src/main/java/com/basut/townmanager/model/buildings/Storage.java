package com.basut.townmanager.model.buildings;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.basut.townmanager.model.Building;
import com.basut.townmanager.utility.enums.BuildingName;
import com.basut.townmanager.utility.enums.BuildingType;
import com.basut.townmanager.utility.enums.Resources;

@Entity
@DiscriminatorValue("Storage")
public class Storage extends Building {
	
	private HashMap<Resources, Long> resourcesInStorage= new HashMap<>();

	public Storage() {
		name =BuildingName.STORAGE;
	}
	
	public Long getResource(Resources res) {
		if (resourcesInStorage.containsKey(res)) {
			return resourcesInStorage.get(res);
		}
		return 0L;
	}
	
	public Set<Entry<Resources, Long>> getResources() {
		return resourcesInStorage.entrySet();
	}
	
	public void addResource(Resources res, Long resValueToAdd) {
		if (resourcesInStorage.containsKey(res)) {
			resourcesInStorage.put(res, resourcesInStorage.get(res)+resValueToAdd);
		} else {
			resourcesInStorage.put(res, resValueToAdd);
		}
	}
	
	public void removeResource(Resources res, Long resValueToAdd) {
		if (resourcesInStorage.containsKey(res)) {
			Long newStorageValue = resourcesInStorage.get(res)-resValueToAdd;
			if(newStorageValue<0) {
				newStorageValue =0L;
			}
			resourcesInStorage.put(res, newStorageValue);
		} 
	}
	
	@Override
	public boolean upgrade() {
		return false;
	}
	
	@Override
	public BuildingType getType() {
		return BuildingType.STORAGE;
	}
}