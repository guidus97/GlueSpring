package com.example.GlueSpring.utils;

import java.util.logging.Logger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggerFactory {
	
	@Bean
	public Logger getInstanceLogger() {
		return Logger.getLogger("GlueSpring system logger");
	}
	
	
}
