package com.training.jwt.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.training.jwt.model.StandardResponse;
import com.training.jwt.model.User;
import com.training.jwt.service.AuthenticationService;

@RestController
public class AuthenticationController {

	@Autowired
	AuthenticationService authenticationService;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	@CrossOrigin
	public StandardResponse<Map<String, Object>> authenticateUser(@RequestBody User user) {
		return authenticationService.authenticate(user.getEmailId(), user.getPassword());
	}

	@RequestMapping(value = "/verify", method = RequestMethod.GET)
	@CrossOrigin
	public StandardResponse validateUser(@RequestHeader(value = "jwt-token") String jwtToken) {
		return authenticationService.verifyToken(jwtToken);

	}

}
