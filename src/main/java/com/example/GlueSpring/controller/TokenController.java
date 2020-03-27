package com.example.GlueSpring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.GlueSpring.service.TokenService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/token")
@Api(value = "Token controller", description = "Controller for token request")
public class TokenController {
	
	@Autowired
	private TokenService tokenService;
	
	@RequestMapping(value = "/getToken", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "GET method for token request", httpMethod = "GET")
	@ApiResponses(value = {
			@ApiResponse(message = "Token success", code = 200),
			@ApiResponse(message = "Unauthorized to get token", code = 401),
			@ApiResponse(message = "Internal server error", code = 500)
	})
	public ResponseEntity<String> getToken(){
		
		return tokenService.getToken();
	}
}
