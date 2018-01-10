package com.training.jwt.service.impl;

import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.jwt.constants.Constants;
import com.training.jwt.exception.CustomWebException;
import com.training.jwt.model.StandardResponse;
import com.training.jwt.model.User;
import com.training.jwt.model.cassandraudt.DepartmentUdt;
import com.training.jwt.model.cassandraudt.PrivilegeUdt;
import com.training.jwt.model.cassandraudt.RoleUdt;
import com.training.jwt.service.AuthenticationService;
import com.training.jwt.service.UserService;
import com.training.jwt.utils.CommonUtils;
import com.training.jwt.utils.PasswordUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component("authenticationService")
public class AuthenticationServiceImpl implements AuthenticationService, Constants {

	@Autowired
	UserService userService;

	@Override
	public StandardResponse<Map<String, Object>> authenticate(String emailId, String password) {
		User user = userService.getUserByEmailId(emailId);
		if (CommonUtils.isNull(user)) {

			throw new CustomWebException(ERROR, "User not found.");
		}
		if (user.getPassword().equals(PasswordUtil.getPasswordHash(password))) {
			Map<String, Object> token = issueToken(user);
			StandardResponse<Map<String, Object>> stdResponse = new StandardResponse<Map<String, Object>>();
			stdResponse.setStatus(Constants.SUCCESS);
			stdResponse.setElement(token);
			return stdResponse;
		} else {
			throw new CustomWebException(ERROR, "Invalid password.");
		}
	}

	@Override
	public Map<String, Object> issueToken(User user) {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);

		// We will sign our JWT with our ApiKey secret
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary("test");
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

		List<PrivilegeUdt> privileges = new ArrayList<PrivilegeUdt>();

		Set<RoleUdt> roles = user.getRoles();

		for (RoleUdt role : roles) {
			privileges.add(role.getPrivilege());
		}

		// Let's set the JWT Claims
		JwtBuilder builder = Jwts.builder().setId(String.valueOf(user.getId())).setIssuedAt(now)
				.setSubject(user.getFirstName() + " " + user.getLastName()).setIssuer("Training Manager")
				.claim(FIRST_NAME, user.getFirstName()).claim(LAST_NAME, user.getLastName())
				.claim(EMAIL_ID, user.getEmailId()).claim("departments", user.getDepartments())
				.claim("privileges", privileges).signWith(signatureAlgorithm, signingKey);

		System.out.println("Change detected");

		// if it has been specified, let's add the expiration
		long expMillis = nowMillis + 600000000;
		Date exp = new Date(expMillis);
		builder.setExpiration(exp);

		// Builds the JWT and serializes it to a compact, URL-safe string
		Map<String, Object> token = new HashMap<String, Object>();
		token.put(JWT_TOKEN, builder.compact());
		return token;
	}

	@Override
	public StandardResponse<Map<String, Object>> verifyToken(String jwt) {
		try {

			Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary("test")).parseClaimsJws(jwt)
					.getBody();
			Map<String, Object> userData = new HashMap<String, Object>();
			userData.put(ID, claims.getId());
			userData.put(FIRST_NAME, claims.get(FIRST_NAME));
			userData.put(LAST_NAME, claims.get(LAST_NAME));
			ObjectMapper mapper = new ObjectMapper();
			Set<DepartmentUdt> departmentList = mapper.convertValue(claims.get("departments"),
					new TypeReference<Set<DepartmentUdt>>() {
					});
			userData.put("departments", departmentList);
			userData.put(EMAIL_ID, claims.get(EMAIL_ID));
			userData.put("privileges", claims.get("privileges"));
			StandardResponse<Map<String, Object>> stdResponse = new StandardResponse<Map<String, Object>>();
			stdResponse.setStatus(Constants.SUCCESS);
			stdResponse.setElement(userData);
			return stdResponse;
		} catch (Exception e) {
			throw new CustomWebException(ERROR, "Invalid JWT token");
		}

	}

}
