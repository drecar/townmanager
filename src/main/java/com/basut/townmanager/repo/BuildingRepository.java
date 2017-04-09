package com.basut.townmanager.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.basut.townmanager.model.Building;

public interface BuildingRepository extends JpaRepository<Building, Long> {

}
