package com.training.jwt.service;

import java.util.Map;

import com.training.jwt.model.StandardResponse;
import com.training.jwt.model.User;

public interface AuthenticationService {

	public StandardResponse<Map<String, Object>> authenticate(String emailId, String password);

	public Map<String, Object> issueToken(User user);

	public StandardResponse<Map<String, Object>> verifyToken(String jwt);
}
