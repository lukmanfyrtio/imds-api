package com.idx.imds.api.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idx.imds.api.core.enums.ImdsErrorEnum;
import com.idx.imds.api.core.exception.ImdsException;
import com.idx.imds.api.persistence.entity.CustomerUser;
import com.idx.imds.api.service.CustomerUserService;
import com.idx.imds.api.service.TokenValidationService;

@Service
public class TokenValidationServiceImpl implements TokenValidationService{
	private static Logger log = LogManager.getLogger(TokenValidationServiceImpl.class);

	@Autowired
	private CustomerUserService customerUserService;
	
	@Override
	public CustomerUser validate(String tokenId, String token) {
		CustomerUser cu = customerUserService.getByTokenIdAndToken(tokenId, token);
		if(cu == null) {
			log.info("INVALID");
			throw new ImdsException(ImdsErrorEnum.INVALID_TOKEN);
		}
		return cu;
		
	}
}
