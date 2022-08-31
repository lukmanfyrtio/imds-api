package com.idx.imds.api.controller.endpoint;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.idx.imds.api.controller.contract.GetConfluentTokenResponse;
import com.idx.imds.api.controller.contract.GetTopicRequest;
import com.idx.imds.api.controller.contract.GetTopicResponse;
import com.idx.imds.api.core.enums.ImdsErrorEnum;
import com.idx.imds.api.core.exception.ImdsException;
import com.idx.imds.api.service.CustomerUserService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("")
public class TopicController {
	private static Logger log = LogManager.getLogger(TopicController.class);

	@Autowired
	private CustomerUserService customerUserService;
	@PostMapping(value = "/topics")
	@ResponseBody
	public ResponseEntity<GetTopicResponse> getToken(@RequestBody GetTopicRequest request) {
		GetTopicResponse response = new GetTopicResponse();
		try {
			log.info("Try to renew token...");

			List<String> topicList = customerUserService.getTopic(request.getTokenId(), request.getToken());
			response.setTopicNames(topicList);
			response.setCode(ImdsErrorEnum.SUCCESS.getCode());
		} catch (ImdsException e) {
			response.setCode(e.getImdsErrorEnum().getCode());
			response.setDescription(e.getImdsErrorEnum().getDescription());
		}

		return new ResponseEntity<>(response, HttpStatus.OK); 
	}

}
