package com.idx.imds.api.controller.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages = { "com.idx.imds.api" })
@EnableJpaRepositories(basePackages = "com.idx.imds.api.persistence.repository")
@EnableTransactionManagement
@EntityScan(basePackages = "com.idx.imds.api.persistence.entity")
public class ImdsApiConfiguration {
	private static Logger logger = LogManager.getLogger(ImdsApiConfiguration.class);
	static {
		logger.debug("Creating bean from : ImdsApiConfiguration!");
	}
}
