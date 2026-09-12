package com.planner.TourVista.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.planner.TourVista.Config.PlacesProperties;



@SpringBootTest
class PlacesPropertiesTest {
    

    @Autowired
    private PlacesProperties placesProperties;

    @Test
    void placesProperties_shouldBeLoaded() {

        assertEquals(
        "https://api.geoapify.com",
        placesProperties.getBaseUrl()
);
    }

    
}