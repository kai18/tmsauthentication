package com.training.jwt.service;

import com.training.jwt.model.User;

public interface UserService {
	
	public User getUserByEmailId(String emailId);
	
	public void saveUser(User user);

}
