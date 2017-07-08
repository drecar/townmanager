package com.basut.townmanager.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.basut.townmanager.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	 User findByEmail(String email);
}
