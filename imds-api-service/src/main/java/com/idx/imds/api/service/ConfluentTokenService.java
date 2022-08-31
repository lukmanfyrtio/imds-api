package com.idx.imds.api.service;

import java.util.Date;

import com.idx.imds.api.persistence.dto.ConfluentTokenDto;
import com.idx.imds.api.persistence.dto.GetTokenDto;
import com.idx.imds.api.persistence.dto.RenewTokenDto;

public interface ConfluentTokenService {

	ConfluentTokenDto getToken(GetTokenDto getToken);
	
	Date renewToken(RenewTokenDto renewTokenDto);

}
