package com.example.GlueSpring.service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.GlueSpring.mainObject.ObjectGeneral;
import com.example.GlueSpring.utils.LoggerFactory;

@Service
public class TokenService {

	@Autowired
	private LoggerFactory logger;
	
	@Autowired
	private RestTemplate restTemplate;
	
	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public ResponseEntity<String> getToken(){
		
		HttpHeaders header = new HttpHeaders();
		header.set("Authorization", "Basic " + encodeCredentials(ObjectGeneral.mpxUser + ":" + ObjectGeneral.mpxPass));
		
		HttpEntity<String> request = new HttpEntity<>(header);
		ResponseEntity<String> response = restTemplate.exchange(ObjectGeneral.mpxSignInUrl, HttpMethod.GET, request, String.class);
		
		if (response.getStatusCodeValue() == 200) {
			
			logger.getInstanceLogger().info(response.getBody());
			
			JSONObject json = new JSONObject(response.getBody());
			ObjectGeneral.mpxToken = json.getJSONObject("signInResponse").getString("token");			
			return new ResponseEntity<String>(HttpStatus.OK);
		}
		
		else if (response.getStatusCodeValue() == 401) {
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		}
		
		else {
			
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	private String encodeCredentials(String credentials) {
		
		byte[] bytes = Base64.getEncoder().encode(credentials.getBytes());
		
		return new String (bytes, StandardCharsets.UTF_8);
	}
	
}
