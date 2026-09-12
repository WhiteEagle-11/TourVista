package com.planner.TourVista.Services;

import org.springframework.stereotype.Service;

import com.planner.TourVista.Client.WeatherClient;
import com.planner.TourVista.dto.WeatherResponse;

@Service
public class WeatherService {

    private final WeatherClient weatherClient;

    public WeatherService(WeatherClient weatherClient) {
        this.weatherClient = weatherClient;
    }

    public WeatherResponse getWeather(double latitude, double longitude) {
        return weatherClient.getWeather(latitude, longitude);
    }
}