package com.basut.townmanager.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.basut.townmanager.model.Dungeon;

public interface IDungeonRepository extends JpaRepository<Dungeon, Long> {

}
