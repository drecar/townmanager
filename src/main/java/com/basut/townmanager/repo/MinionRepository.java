package com.basut.townmanager.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.JpaRepositoryNameSpaceHandler;
import org.springframework.stereotype.Repository;

import com.basut.townmanager.model.Minion;

@Repository
public interface MinionRepository extends JpaRepository<Minion, Long>{

}
