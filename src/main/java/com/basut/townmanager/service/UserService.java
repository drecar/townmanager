package com.basut.townmanager.service;

import com.basut.townmanager.model.User;

public interface UserService {
	public User findUserByEmail(String email);
	public void saveUser(User user);
}
