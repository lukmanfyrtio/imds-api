package com.idx.imds.api.service.impl;

import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dtt.generic.service.services.impl.GeneralServiceImpl;
import com.google.gson.Gson;
import com.idx.imds.api.core.enums.ImdsErrorEnum;
import com.idx.imds.api.core.exception.ImdsException;
import com.idx.imds.api.core.util.DateDiffUtil;
import com.idx.imds.api.persistence.dto.ConfluentTokenDto;
import com.idx.imds.api.persistence.dto.GetTokenDto;
import com.idx.imds.api.persistence.dto.RenewTokenDto;
import com.idx.imds.api.persistence.entity.CorePackage;
import com.idx.imds.api.persistence.entity.CustomerUser;
import com.idx.imds.api.persistence.repository.CustomerUserRepository;
import com.idx.imds.api.service.ConfluentTokenService;
import com.idx.imds.api.service.ConfluentTokenServiceFactory;
import com.idx.imds.api.service.CustomerUserService;
import com.idx.imds.api.service.CustomerValidationService;
import com.idx.imds.api.service.TokenValidationService;

@Service
public class CustomerUserServiceImpl extends GeneralServiceImpl<CustomerUser, Long> implements CustomerUserService {

	private static Logger log = LogManager.getLogger(CustomerUserServiceImpl.class);

	private CustomerUserRepository customerUserRepository;

	@Autowired
	public void setCustomerUserRepository(CustomerUserRepository customerUserRepository) {
		this.customerUserRepository = customerUserRepository;
		this.repository = customerUserRepository;
	}

	@Autowired
	private ConfluentTokenServiceFactory confluentTokenServiceFactory;

	@Autowired
	private CustomerValidationService customerValidationService;
	@Autowired
	private TokenValidationService tokenValidationService;

	@Override
	public CustomerUser getByUserName(String username) {
		return customerUserRepository.getByUserName(username);
	}

	@Override
	public ConfluentTokenDto getToken(String username) {
		CustomerUser user = this.getByUserName(username);
		/*
		 * 02-03-2022 Konfirmasi IDX, user expired by username
		 */
		ConfluentTokenDto confluentToken = new ConfluentTokenDto();
		/*
		 * Validate Expired Time Have package/not
		 */
		customerValidationService.validate(user);

		/*
		 * 1 USER 1 PAKET
		 */
		CorePackage corePackage = user.getCustomerPackage().get(0).getPackages();

		if (user.getTokenId() == null) {

			Date currentDate = new Date();
			currentDate.setMinutes(0);
			currentDate.setHours(0);
			currentDate.setSeconds(0);
			Long maxLifeTime = DateDiffUtil.getDateDiffMilliSecond(currentDate, user.getExpiredDate());
			GetTokenDto getToken = new GetTokenDto();
			getToken.setMaxLifeTime(maxLifeTime);
			getToken.setUserIdPackage(corePackage.getPackageUser());
			getToken.setUserSecretPackage(corePackage.getPackageCred());
			confluentToken = confluentTokenServiceFactory.getTokenService(user.getIsSandbox()).getToken(getToken);
		} else {
			confluentToken.setToken(user.getTokenConfluent());
			confluentToken.setTokenId(user.getTokenId());
			confluentToken.setCreatedTime(user.getTokenCreatedTime());
			confluentToken.setExpiredTime(user.getTokenExpiredTime());
			confluentToken.setMaxLifeTime(user.getTokenMaxLifeTime());
		}

		confluentToken.setPackageCode(corePackage.getPackageCode());
		confluentToken.setPackageName(corePackage.getPackageName());
		confluentToken.setConsumerGroup(corePackage.getPackageUser()+username);
		return confluentToken;

	}
	@Override
	public ConfluentTokenDto renewToken(String tokenId, String token) {
		/*
		 * 02-03-2022 Konfirmasi IDX, user expired by username
		 */
		ConfluentTokenDto confluentToken = new ConfluentTokenDto();
		/*
		 * Validate Expired Time Have package/not
		 */
		CustomerUser user = tokenValidationService.validate(tokenId, token);

		/*
		 * 1 USER 1 PAKET
		 */
		CorePackage corePackage = user.getCustomerPackage().get(0).getPackages();

		if (user.getTokenId() != null) {
			Date currentDate = new Date();
			currentDate.setMinutes(0);
			currentDate.setHours(0);
			currentDate.setSeconds(0);
			Long expiredMilisecond = DateDiffUtil.getDateDiffMilliSecond(currentDate, user.getExpiredDate());
			RenewTokenDto renewToken = new RenewTokenDto();
			renewToken.setToken(user.getTokenConfluent());
			renewToken.setTokenId(user.getTokenId());
			renewToken.setUserIdPackage(corePackage.getPackageUser());
			renewToken.setUserSecretPackage(corePackage.getPackageCred());
			renewToken.setExpiredMilisecond(expiredMilisecond);
			Date expiredTime = confluentTokenServiceFactory.getTokenService(user.getIsSandbox()).renewToken(renewToken);
			confluentToken.setExpiredTime(expiredTime);
			
			user.setTokenExpiredTime(expiredTime);
			this.save(user);
		} else {
			throw new ImdsException(ImdsErrorEnum.NOTHING_TOKEN_GENERATED);
		}
		confluentToken.setCreatedTime(user.getTokenCreatedTime());
		confluentToken.setMaxLifeTime(user.getTokenMaxLifeTime());
		confluentToken.setToken(user.getTokenConfluent());
		confluentToken.setTokenId(user.getTokenId());
		confluentToken.setPackageCode(corePackage.getPackageCode());
		confluentToken.setPackageName(corePackage.getPackageName());
		confluentToken.setConsumerGroup(corePackage.getPackageUser()+user.getUserName());
		return confluentToken;

	}
	@Override
	public void updateToken(ConfluentTokenDto confluentToken, String username) {
		customerUserRepository.updateToken(confluentToken.getToken(), confluentToken.getTokenId(), username,
				confluentToken.getCreatedTime(), confluentToken.getExpiredTime(), confluentToken.getMaxLifeTime());
	}
	@Override
	public CustomerUser getByTokenIdAndToken(String tokenId, String token) {
		return customerUserRepository.getByTokenIdAndToken(tokenId, token);
	}

	@Override
	public List<String> getTopic(String tokenId, String token) {
		CustomerUser user = tokenValidationService.validate(tokenId, token);

		/*
		 * 1 USER 1 PAKET
		 */
		CorePackage corePackage = user.getCustomerPackage().get(0).getPackages();
		corePackage.getTopicName();
		List<String> topics = new ArrayList<>();
		topics = new Gson().fromJson(corePackage.getTopicName(), List.class);
		return topics;
	}
}
