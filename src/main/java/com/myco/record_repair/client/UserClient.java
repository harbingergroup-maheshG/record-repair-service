package com.myco.record_repair.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.myco.record_repair.model.UserModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserClient {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserClient.class);
	private static final String URI = "http://localhost:8080/";

	public ResponseEntity<String> sendCollectedDataToUser(UserModel userModel, String url) {
		LOGGER.info(">>>> sendCollectedDataToUser() , User Model : {} , Url path : {} ", userModel, url);
		RestTemplate restTemplate = new RestTemplate();

		try {
			StringBuilder resourceUrl = new StringBuilder();
			resourceUrl.append(URI).append(url);

			// Create the request body by wrapping
			// the object in HttpEntity
			HttpEntity<UserModel> request = new HttpEntity<>(userModel);
			LOGGER.info(">>>> sendCollectedDataToUser() , request :{} , resourceUrl : {} ", request, resourceUrl);
			ResponseEntity<String> response = restTemplate.exchange(resourceUrl.toString(), HttpMethod.POST, request,
					String.class);
			LOGGER.info("<<<< sendCollectedDataToUser() , response :{} ", response);
			return response;
		} catch (Exception e) {
			log.error(">>>> error while calling 'user' , exception : {} ", e.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
	}

}
