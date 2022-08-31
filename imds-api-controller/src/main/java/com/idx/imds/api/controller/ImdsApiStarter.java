package com.idx.imds.api.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.idx.imds.api.controller.configuration.ImdsApiConfiguration;

@SpringBootApplication
@Import({ ImdsApiConfiguration.class })
@EnableTransactionManagement
public class ImdsApiStarter extends SpringBootServletInitializer {
	private final static Logger log = LogManager.getLogger(ImdsApiStarter.class);

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ImdsApiStarter.class);
	}

	public static void main(String[] args) {
		log.info("Starting IMDS API...");
		SpringApplication.run(ImdsApiStarter.class, args);
	}
}