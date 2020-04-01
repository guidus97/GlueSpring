package com.example.GlueSpring;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.example.GlueSpring.controller.TokenController;
import com.example.GlueSpring.service.TokenService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TokenService.class, TokenController.class})
public class TokenServiceTest {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private TokenService tokenService;
	
	@LocalServerPort
	private int localServerPort;
	
	@Test
	public void getTokenTest() {
		
		ResponseEntity<String> response = tokenService.getToken();
		ResponseEntity<String> testResponse = restTemplate.getForEntity("http://localhost:" + localServerPort + "/token/getToken", String.class);
		
		assertTrue(response.getHeaders().containsKey("Authorization"));
		assertEquals(response.getStatusCodeValue(), 200);
		assertTrue(response.getBody().contains("token"));
		assertEquals(testResponse, response);
	}
}
