package com.basut.townmanager.model.buildings;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;

import com.basut.townmanager.model.Building;
import com.basut.townmanager.utility.enums.BuildingName;
import com.basut.townmanager.utility.enums.BuildingType;
import com.basut.townmanager.utility.enums.TownResource;

@Entity
@DiscriminatorValue("Storage")
public class Storage extends Building {
	
	@ElementCollection(fetch = FetchType.EAGER)
	private Map<TownResource, Long> resourcesInStorage= new HashMap<>();

	public Storage() {
		name =BuildingName.STORAGE;
	}
	
	public Long getResource(TownResource res) {
		if (resourcesInStorage.containsKey(res)) {
			return resourcesInStorage.get(res);
		}
		return 0L;
	}
	
	public Set<Entry<TownResource, Long>> getResources() {
		return resourcesInStorage.entrySet();
	}
	
	public void addResource(TownResource res, Long resValueToAdd) {
		if (resourcesInStorage.containsKey(res)) {
			resourcesInStorage.put(res, resourcesInStorage.get(res)+resValueToAdd);
		} else {
			resourcesInStorage.put(res, resValueToAdd);
		}
	}
	
	public void removeResource(TownResource res, Long resValueToAdd) {
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