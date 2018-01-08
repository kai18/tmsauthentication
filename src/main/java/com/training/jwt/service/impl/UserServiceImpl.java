package com.training.jwt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.training.jwt.constants.Constants;
import com.training.jwt.model.User;
import com.training.jwt.repository.UserRepository;
import com.training.jwt.service.UserService;

@Component
public class UserServiceImpl implements UserService, Constants {

	@Autowired
	private UserRepository userRepository;

	@Override
	public User getUserByEmailId(String emailId) {
		return userRepository.findByEmailId(emailId);
	}

	@Override
	public void saveUser(User user) {
		// TODO Auto-generated method stub
		
	}

	/*@Override
	public void saveUser(User user) {
		try {
			if (null != user) {
				Set<Integer> roleIds = user.getRoleIds();
				roleRepository.getRoleListByRoleIds(roleIds).forEach(role -> {
					Set<Integer> userIds = role.getUserIds();
					userIds.add(user.getId());
					roleRepository.save(role);
				});

			}
			userRepository.save(user);
		} catch (Exception e) {
			throw new CustomWebException(ERROR, e.getMessage().toString());
		}
	}*/

}
