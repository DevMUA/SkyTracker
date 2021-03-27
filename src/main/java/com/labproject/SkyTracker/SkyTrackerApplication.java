package com.labproject.SkyTracker;

import com.fasterxml.jackson.databind.JsonNode;
import com.labproject.SkyTracker.OpenSky.OpenSkyController;
import com.labproject.SkyTracker.SkyTrackerAPI.SkyTrackerController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@SpringBootApplication
public class SkyTrackerApplication {



	public static void main(String[] args) {
		SpringApplication.run(SkyTrackerApplication.class, args);


	}

}
