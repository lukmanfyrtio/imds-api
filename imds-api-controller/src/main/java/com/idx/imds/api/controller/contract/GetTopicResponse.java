package com.idx.imds.api.controller.contract;

import java.util.List;

public class GetTopicResponse extends BaseResponse{
	
	private List<String> topicNames;

	public List<String> getTopicNames() {
		return topicNames;
	}

	public void setTopicNames(List<String> topicNames) {
		this.topicNames = topicNames;
	}
	
	



}
