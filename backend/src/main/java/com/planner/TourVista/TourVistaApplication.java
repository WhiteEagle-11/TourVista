package com.planner.TourVista;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.planner.TourVista.Config.PlacesProperties;

@SpringBootApplication
@EnableConfigurationProperties(PlacesProperties.class)
public class TourVistaApplication {

	public static void main(String[] args) {
		SpringApplication.run(TourVistaApplication.class, args);
	}

}
