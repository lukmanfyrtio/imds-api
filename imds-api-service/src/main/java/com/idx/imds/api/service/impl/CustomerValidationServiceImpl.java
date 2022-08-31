package com.idx.imds.api.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.idx.imds.api.core.enums.ImdsErrorEnum;
import com.idx.imds.api.core.exception.ImdsException;
import com.idx.imds.api.persistence.entity.CustomerUser;
import com.idx.imds.api.service.CustomerValidationService;

@Service
public class CustomerValidationServiceImpl implements CustomerValidationService{

	private static Logger log = LogManager.getLogger(CustomerValidationServiceImpl.class);
	@Override
	public void validate(CustomerUser user) {
		
		if(user.getExpiredDate()!=null ) {

			LocalDate blokLimitLocalDate = user.getExpiredDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			blokLimitLocalDate = blokLimitLocalDate.plusDays(1);

			LocalDate currentDate = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

			Boolean result = currentDate.isBefore(blokLimitLocalDate);
			log.info("currentDate:" + currentDate.toString());
			log.info("suspendDate:" + blokLimitLocalDate.toString());
			log.info(result);
			if(!result) {
				throw new ImdsException(ImdsErrorEnum.USER_HAS_EXPIRED);
			}
		}


		if (user.getCustomerPackage() == null || user.getCustomerPackage().isEmpty()) {

			throw new ImdsException(ImdsErrorEnum.NOT_HAVE_PACKAGE);
		
		}

		
	}
	
}
