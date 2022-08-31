package com.idx.imds.api.service.impl;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.CreateDelegationTokenOptions;
import org.apache.kafka.clients.admin.RenewDelegationTokenOptions;
import org.apache.kafka.clients.admin.RenewDelegationTokenResult;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.security.auth.KafkaPrincipal;
import org.apache.kafka.common.security.token.delegation.DelegationToken;
import org.apache.kafka.common.security.token.delegation.TokenInformation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idx.imds.api.persistence.dto.ConfluentTokenDto;
import com.idx.imds.api.persistence.dto.GetTokenDto;
import com.idx.imds.api.persistence.dto.RenewTokenDto;
import com.idx.imds.api.service.ConfluentTokenService;
import com.idx.imds.api.service.configuration.ConfluentConfiguration;

@Service("confluentTokenService")
public class ConfluentTokenServiceImpl implements ConfluentTokenService {

	private static Logger log = LogManager.getLogger(ConfluentTokenServiceImpl.class);
	@Autowired
	private ConfluentConfiguration confluentConfig;

	@Override
	public ConfluentTokenDto getToken(GetTokenDto getToken) {

		final String userID = getToken.getUserIdPackage();
		final String userSecret = getToken.getUserSecretPackage();
		final String bootstraps = confluentConfig.getBootstraps();
		final Long maxLifeTime = getToken.getMaxLifeTime();
		final String renewerID = userID;
		final String protocol = "SASL_SSL";
		final String saslmechanism = "PLAIN";
		final String truststorelocation = confluentConfig.getTrustStoreLocation();
		final String truststorepass = confluentConfig.getTrustStorePass();
//		Integer maxlifetime = 3600000; // 60minute
		String jaasconfig = String.format(
				"org.apache.kafka.common.security.plain.PlainLoginModule required username='%s' password='%s';", userID,
				userSecret);
		Properties config = new Properties(); // Preparing AdminClient Config
		config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstraps);
		config.put(AdminClientConfig.SECURITY_PROTOCOL_CONFIG, protocol);
		config.put(SaslConfigs.SASL_MECHANISM, saslmechanism);
		config.put(SaslConfigs.SASL_JAAS_CONFIG, jaasconfig);
		config.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, truststorelocation);
		config.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, truststorepass);
		AdminClient admin = AdminClient.create(config); // Create AdminClient
		log.info("CreateToken");
		log.info("userID:" + userID);
		log.info("userSecret:" + userSecret);
		log.info("maxLifeTime:" + maxLifeTime);
		log.info("bootstraps:" + bootstraps);
		try {
			KafkaPrincipal renewer = new KafkaPrincipal("User", renewerID);
			List<KafkaPrincipal> listrenewer = new ArrayList<KafkaPrincipal>();
			listrenewer.add(renewer);
			CreateDelegationTokenOptions tokenop = new CreateDelegationTokenOptions();
			// Scanner scanner = new Scanner(System.in);
			// System.out.print("Insert Expire Lenght in ms: ");
			// Integer maxlifetime = scanner.nextInt();
			// scanner.close();
			tokenop.maxlifeTimeMs(maxLifeTime);
			tokenop.renewers(listrenewer);
			KafkaFuture<DelegationToken> future = admin.createDelegationToken(tokenop).delegationToken();
			DelegationToken delegated = future.get();
			TokenInformation tokeninfo = delegated.tokenInfo();
			// log.info(delegated);
			// log.info(tokeninfo);
			DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
			long issuetime = tokeninfo.issueTimestamp();
			long maxtime = tokeninfo.maxTimestamp();
			long expirytime = tokeninfo.expiryTimestamp();
			String issuedatetime = Instant.ofEpochMilli(issuetime).atZone(ZoneId.systemDefault()).toLocalDateTime()
					.format(format);
			String maxdatetime = Instant.ofEpochMilli(maxtime).atZone(ZoneId.systemDefault()).toLocalDateTime()
					.format(format);
			String expirydatetime = Instant.ofEpochMilli(expirytime).atZone(ZoneId.systemDefault()).toLocalDateTime()
					.format(format);
			String hmacstring = delegated.hmacAsBase64String();
			log.info("---------------------------------");
			log.info("Owner: " + tokeninfo.ownerAsString());
			log.info("Renewer: " + tokeninfo.renewersAsString());
			log.info("IssueTime: " + issuedatetime);
			log.info("MaxTime: " + maxdatetime);
			log.info("ExpireTime: " + expirydatetime);
			log.info("TokenID " + tokeninfo.tokenId());
			log.info("Base64hmac: " + hmacstring);
			// log.info("HashCode: "+tokeninfo.hashCode());
			log.info("---------------------------------");
			ConfluentTokenDto confluentToken = new ConfluentTokenDto();
			confluentToken.setToken(hmacstring);
			confluentToken.setTokenId(tokeninfo.tokenId());
			confluentToken.setCreatedTime(new Date(issuetime));
			confluentToken.setMaxLifeTime(new Date(maxtime));
			confluentToken.setExpiredTime(new Date(expirytime));
			return confluentToken;
		}

		catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public Date renewToken(RenewTokenDto renewTokenDto) {

		final String userID = renewTokenDto.getUserIdPackage();
		final String userSecret = renewTokenDto.getUserSecretPackage();
		final String bootstraps = confluentConfig.getBootstraps();
		String protocol = "SASL_SSL";
		String saslmechanism = "PLAIN";
		final String truststorelocation = confluentConfig.getTrustStoreLocation();
		final String truststorepass = confluentConfig.getTrustStorePass();
//		Integer renewtime = 7200000; // 60minute
		final Long renewtime = renewTokenDto.getExpiredMilisecond(); // 60minute
		String jaasconfig = String.format(
				"org.apache.kafka.common.security.plain.PlainLoginModule required username='%s' password='%s';", userID,
				userSecret);
		Properties config = new Properties(); // Preparing AdminClient Config
		config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstraps);
		config.put(AdminClientConfig.SECURITY_PROTOCOL_CONFIG, protocol);
		config.put(SaslConfigs.SASL_MECHANISM, saslmechanism);
		config.put(SaslConfigs.SASL_JAAS_CONFIG, jaasconfig);
		config.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, truststorelocation);
		config.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, truststorepass);
		AdminClient admin = AdminClient.create(config);
		log.info("RenewToken");
		try {
			byte[] decodedBytes = Base64.getDecoder().decode(renewTokenDto.getToken());
			RenewDelegationTokenOptions renewopt = new RenewDelegationTokenOptions();
			renewopt.renewTimePeriodMs(renewtime);
			RenewDelegationTokenResult result = admin.renewDelegationToken(decodedBytes, renewopt);
			KafkaFuture<Long> future = result.expiryTimestamp();
			Long expirytimestamp = future.get();
			DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
			String expirydatetime = Instant.ofEpochMilli(expirytimestamp).atZone(ZoneId.systemDefault())
					.toLocalDateTime().format(format);
			log.info("---------------------------------");
			log.info(result.toString());
			log.info("ExpireTime: " + expirydatetime);
			
			
			log.info("---------------------------------\n");
			return new Date(expirytimestamp);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return null;
	}
}
