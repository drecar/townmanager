package com.basut.townmanager.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.basut.townmanager.model.Town;

public interface TownRepository extends JpaRepository<Town, Long>{

}
