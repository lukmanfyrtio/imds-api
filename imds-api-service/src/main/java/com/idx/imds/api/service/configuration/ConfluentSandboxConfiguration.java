package com.idx.imds.api.service.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app.sandbox.confluent")
public class ConfluentSandboxConfiguration {
	private String bootstraps;
	private String trustStoreLocation;
	private String trustStorePass;
	public String getBootstraps() {
		return bootstraps;
	}
	public void setBootstraps(String bootstraps) {
		this.bootstraps = bootstraps;
	}
	public String getTrustStoreLocation() {
		return trustStoreLocation;
	}
	public void setTrustStoreLocation(String trustStoreLocation) {
		this.trustStoreLocation = trustStoreLocation;
	}
	public String getTrustStorePass() {
		return trustStorePass;
	}
	public void setTrustStorePass(String trustStorePass) {
		this.trustStorePass = trustStorePass;
	}
	
	

}
