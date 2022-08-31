package com.idx.imds.api.service;

import com.idx.imds.api.persistence.entity.CustomerUser;

public interface TokenValidationService {

	CustomerUser validate(String tokenId, String token);
}
