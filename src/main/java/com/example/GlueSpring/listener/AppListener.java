package com.example.GlueSpring.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.example.GlueSpring.mainObject.ObjectGeneral;

@Component
@PropertySource("classpath:app.properties")
public class AppListener implements ApplicationListener<ApplicationStartedEvent> {

	@Autowired
	private Environment environment;
	
	@Override
	public void onApplicationEvent(ApplicationStartedEvent event) {

		ObjectGeneral.mpxUser = environment.getProperty("mpxSignInUser");
		ObjectGeneral.mpxPass = environment.getProperty("mpxSignInPass");
		ObjectGeneral.mpxSignInUrl = environment.getProperty("mpxSignInUrl");
		ObjectGeneral.mpxStations = environment.getProperty("mpxStationsUrl");
		ObjectGeneral.mpxListing = environment.getProperty("mpxListingUrl");
	}

	
}
