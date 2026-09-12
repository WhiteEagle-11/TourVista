package com.planner.TourVista.Client;

import com.planner.TourVista.dto.WeatherResponse;

public interface WeatherClient {

    WeatherResponse getWeather(double latitude, double longitude);
}