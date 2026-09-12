package com.planner.TourVista.dto;

public class WeatherResponse {

    private final double temperature;
    private final double windSpeed;
    private final int weatherCode;

    public WeatherResponse(
            double temperature,
            double windSpeed,
            int weatherCode) {

        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.weatherCode = weatherCode;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public int getWeatherCode() {
        return weatherCode;
    }
}