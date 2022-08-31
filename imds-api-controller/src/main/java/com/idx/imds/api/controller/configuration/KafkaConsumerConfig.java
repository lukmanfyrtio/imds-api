package com.idx.imds.api.controller.configuration;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

	@Bean
	public ConsumerFactory<String, String> consumerFactory() {
		String SASL_PROTOCOL = "SASL_SSL";
		String jaasTemplate = "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"%s\" password=\"%s\";";
		String consJaasCfg = String.format(jaasTemplate, "UserAdminIMDS", "IDXUIDmd2022");
		String TRUSTSTORE_JKS = "E:\\Data\\truststore.jks";
		
		
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
				"DevIMDSConfBrok01:9093, DevIMDSConfBrok02:9093, DevIMDSConfBrok03:9093");
		props.put(ConsumerConfig.GROUP_ID_CONFIG, "widgetGroup");
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		props.put("auto.commit.interval.ms", "1000");
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
				"org.apache.kafka.common.serialization.StringDeserializer");
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
				"org.apache.kafka.common.serialization.StringDeserializer");
		props.put("sasl.mechanism", "PLAIN");
		props.put("sasl.jaas.config", consJaasCfg);
		props.put("security.protocol", SASL_PROTOCOL);
		props.put("ssl.truststore.location", TRUSTSTORE_JKS);
		props.put("ssl.truststore.password", "confluenttruststorepass");
		return new DefaultKafkaConsumerFactory<>(props);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		return factory;
	}
}
