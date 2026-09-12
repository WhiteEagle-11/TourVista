package com.planner.TourVista.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.planner.TourVista.Services.WeatherService;
import com.planner.TourVista.dto.WeatherResponse;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping
    public ResponseEntity<WeatherResponse> getWeather(
            @RequestParam double latitude,
            @RequestParam double longitude) {

        return ResponseEntity.ok(
                weatherService.getWeather(latitude, longitude)
        );
    }
}