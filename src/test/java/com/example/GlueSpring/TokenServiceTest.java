package com.example.GlueSpring;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.hamcrest.CoreMatchers;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.example.SpringConfigTest;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SpringConfigTest.class)
public class TokenServiceTest {

	private RestTemplate restTemplate;

	@Before
	public void setup() {
		restTemplate = new RestTemplate();
	}
	
	@Test
	public void testGetToken_isOk() throws JSONException {
		HttpHeaders header = new HttpHeaders();
		header.set("Authorization", "Basic " + new String(Base64.getEncoder().encode("service/mediaset-test-fincons-apigw:wk5bHOREX8wU".getBytes()), StandardCharsets.UTF_8));
		
		HttpEntity<String> request = new HttpEntity<>(header);
		ResponseEntity<String> response = restTemplate.exchange("https://identity.auth.theplatform.eu/idm/web/Authentication/signIn?schema=1.1&form=json", HttpMethod.GET, request, String.class);
		JSONObject json = new JSONObject(response.getBody());
		
		assertEquals(response.getStatusCodeValue(), 200);
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertTrue(response.getBody().contains("token"));
		assertNotNull(json);
		assertThat(json.getJSONObject("signInResponse").getString("token").length(), CoreMatchers.is(32));
	}
	
	@Test
	public void testGetToken_notAuthorized() {
		ResponseEntity<String> response = restTemplate.getForEntity("https://identity.auth.theplatform.eu/idm/web/Authentication/signIn?schema=1.1&form=json", String.class);
		
		assertEquals(response.getStatusCodeValue(), 200);
		assertTrue(response.getBody().contains("401"));
	}
	
}
