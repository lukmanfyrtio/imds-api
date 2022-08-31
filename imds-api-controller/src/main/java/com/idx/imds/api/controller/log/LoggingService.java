package com.idx.imds.api.controller.log;

import java.io.BufferedReader;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class LoggingService {

	private final Logger log = LogManager.getLogger(LoggingService.class);

	public void logRequest(HttpServletRequest httpServletRequest, Object body) {
		StringBuilder stringBuilder = new StringBuilder();
		Map<String, String> parameters = buildParametersMap(httpServletRequest);

		stringBuilder.append("REQUEST ");
		stringBuilder.append("method=[").append(httpServletRequest.getMethod()).append("] ");
		stringBuilder.append("path=[").append(httpServletRequest.getRequestURI()).append("] ");
		stringBuilder.append("headers=[").append(buildHeadersMap(httpServletRequest)).append("] ");

		if (!parameters.isEmpty()) {
			stringBuilder.append("parameters=[").append(parameters).append("] ");
		}

		if (body != null) {
			try {
				if(body instanceof String) {
					stringBuilder.append("body=[" + body + "]");
				}else {
					stringBuilder.append("body=[" + new ObjectMapper().writeValueAsString(body) + "]");
				}
			} catch (JsonProcessingException e) {
				log.info("FAILED TO WRITE BODY INTO LOG");
			}
		}

		log.info(stringBuilder.toString());
	}

	public void logResponse(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			Object body) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("RESPONSE ");
		stringBuilder.append("method=[").append(httpServletRequest.getMethod()).append("] ");
		stringBuilder.append("path=[").append(httpServletRequest.getRequestURI()).append("] ");
		stringBuilder.append("responseHeaders=[").append(buildHeadersMap(httpServletResponse)).append("] ");
		try {
			stringBuilder.append("responseBody=[").append(new ObjectMapper().writeValueAsString(body)).append("] ");
		} catch (JsonProcessingException e) {
			log.info("FAILED TO WRITE BODY INTO LOG");
		}

		log.info(stringBuilder.toString());
	}

	private Map<String, String> buildParametersMap(HttpServletRequest httpServletRequest) {
		Map<String, String> resultMap = new HashMap<>();
		Enumeration<String> parameterNames = httpServletRequest.getParameterNames();

		while (parameterNames.hasMoreElements()) {
			String key = parameterNames.nextElement();
			String value = httpServletRequest.getParameter(key);
			resultMap.put(key, value);
		}

		return resultMap;
	}

	private Map<String, String> buildHeadersMap(HttpServletRequest request) {
		Map<String, String> map = new HashMap<>();

		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String key = (String) headerNames.nextElement();
			String value = request.getHeader(key);
			map.put(key, value);
		}

		return map;
	}

	private Map<String, String> buildHeadersMap(HttpServletResponse response) {
		Map<String, String> map = new HashMap<>();

		Collection<String> headerNames = response.getHeaderNames();
		for (String header : headerNames) {
			map.put(header, response.getHeader(header));
		}

		return map;
	}

	public String getBodyRequest(HttpServletRequest req) {
		try {
			StringBuilder jb = new StringBuilder();
			String line = null;
			BufferedReader reader = req.getReader();
			while ((line = reader.readLine()) != null)
				jb.append(line);
			return jb.toString();
		} catch (Exception e) {
			log.error("ERROR", e);
			return null;
		}
	}
}
