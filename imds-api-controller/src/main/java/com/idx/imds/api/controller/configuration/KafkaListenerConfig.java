package com.idx.imds.api.controller.configuration;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.idx.imds.api.controller.contract.RealtimePrice;

@Component
public class KafkaListenerConfig {
	private final Logger log = LogManager.getLogger(KafkaListenerConfig.class);
	
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@KafkaListener(topics = "IDX_Real_Time_For_Listed_Company_Widget", groupId = "RLM2")
	public void listenGroupFoo4(String message) {
		String[] data=message.split("\\|");
 		String kodeEfek= data[5].replaceAll("\\s","");
 		String prevPrice= data[7];
 		String openingPrice= data[8];
 		String highPrice= data[9];
 		String lowPrice= data[10];
 		String lastPriceValue= data[11];
 		RealtimePrice realtimePrice=new RealtimePrice();
 		
 		SimpleDateFormat sdf=new SimpleDateFormat("E ,dd MMM yyyy HH:mm:ss a");
 		realtimePrice.setHighPrice(highPrice);
 		realtimePrice.setLastPriceValue(lastPriceValue);
 		realtimePrice.setOpenPrice(openingPrice);
 		realtimePrice.setPrevPrice(prevPrice);
 		realtimePrice.setLowPrice(lowPrice);
 		realtimePrice.setKodeEfek(kodeEfek);
 		realtimePrice.setLastUpdate(sdf.format(new Date()));
 		simpMessagingTemplate.convertAndSendToUser(kodeEfek, "/price", realtimePrice);
 		log.info(message);
	}
	
	
//	@KafkaListener(topics = "ITCH_SUBSET_TV", groupId = "RLM212")
//	public void ITCH_SUBSET_TV(String message) {
// 		log.info("Received Mesage for ITCH_SUBSET_TV"+message);
//	}
//	
//	@KafkaListener(topics = "ITCH_SUBSET_NEWS", groupId = "RLM222")
//	public void ITCH_SUBSET_NEWS(String message) {
// 		log.info("Received Mesage for ITCH_SUBSET_NEWS"+message);
//	}
//	
//	@KafkaListener(topics = "ITCH_SUBSET_BASIC", groupId = "RLM222")
//	public void ITCH_SUBSET_BASIC(String message) {
// 		log.info("Received Mesage for ITCH_SUBSET_BASIC"+message);
//	}
//	
//	@KafkaListener(topics = "ITCH_SUBSET_LASTSALE", groupId = "RLM222")
//	public void ITCH_SUBSET_LASTSALE(String message) {
// 		log.info("Received Mesage for ITCH_SUBSET_LASTSALE"+message);
//	}
}
