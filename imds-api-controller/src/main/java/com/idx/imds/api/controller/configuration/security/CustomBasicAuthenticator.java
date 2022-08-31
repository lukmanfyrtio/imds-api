package com.idx.imds.api.controller.configuration.security;

import static com.idx.imds.api.core.constants.BasicAuthConstant.TOKEN_SEPARATOR;
import static com.idx.imds.api.core.constants.BasicAuthConstant.TOKEN_PREFIX;

import java.util.ArrayList;
import java.util.Base64;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import com.idx.imds.api.service.CustomerUserService;

@Component
public class CustomBasicAuthenticator {

	private final Logger log = LogManager.getLogger(CustomBasicAuthenticator.class);
	
	@Autowired
	private CustomerUserService customerUserService;

	public UsernamePasswordAuthenticationToken getAuthentication(String token) {
		try {
			log.info("TOKEN : "+token);
			String credOnly = token.replace(TOKEN_PREFIX, "");
			String tokenParsed = new String(Base64.getDecoder().decode(credOnly));
			String username = tokenParsed.split(TOKEN_SEPARATOR)[0];
			String password = tokenParsed.split(TOKEN_SEPARATOR)[1];
			
			return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());	
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

}
