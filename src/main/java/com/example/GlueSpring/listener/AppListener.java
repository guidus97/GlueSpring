package com.example.GlueSpring.listener;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.GlueSpring.mainObject.ObjectGeneral;
import com.example.GlueSpring.utils.LoggerFactory;

@Component
@PropertySource("classpath:app.properties")
public class AppListener implements ApplicationListener<ApplicationStartedEvent> {

	@Autowired
	private Environment environment;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private LoggerFactory logger;
	
	@Override
	public void onApplicationEvent(ApplicationStartedEvent event) {

		ObjectGeneral.mpxUser = environment.getProperty("mpxSignInUser");
		ObjectGeneral.mpxPass = environment.getProperty("mpxSignInPass");
		ObjectGeneral.mpxSignInUrl = environment.getProperty("mpxSignInUrl");
		ObjectGeneral.mpxStations = environment.getProperty("mpxStationsUrl");
		ObjectGeneral.mpxListing = environment.getProperty("mpxListingUrl");
	
		new Timer().schedule(new TimerTask() {
			
			@Override
			public void run() {

				logger.getInstanceLogger().info("Inizialization of parameters...");
				
				restTemplate.getForEntity("http://localhost:9090/token/getToken", Void.class);
				
				logger.getInstanceLogger().info(ObjectGeneral.mpxToken);
			}
		
		}, Calendar.getInstance().getTime() , 100000);
		
	}
	
}
