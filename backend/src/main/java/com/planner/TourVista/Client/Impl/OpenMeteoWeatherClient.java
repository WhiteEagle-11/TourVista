package com.planner.TourVista.Client.Impl;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import com.planner.TourVista.Client.WeatherClient;
import com.planner.TourVista.Exception.ExternalApiException;
import com.planner.TourVista.dto.WeatherResponse;

@Component
public class OpenMeteoWeatherClient implements WeatherClient {

    private final RestClient restClient;

    public OpenMeteoWeatherClient(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder
                .baseUrl("https://api.open-meteo.com")
                .build();
    }

    @Override
    public WeatherResponse getWeather(double latitude, double longitude) {

        try {
            OpenMeteoResponse response = restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/v1/forecast")
                            .queryParam("latitude", latitude)
                            .queryParam("longitude", longitude)
                            .queryParam("current", "temperature_2m,wind_speed_10m,weather_code")
                            .build())
                    .retrieve()
                    .body(OpenMeteoResponse.class);

            if (response == null || response.getCurrent() == null) {
                throw new ExternalApiException("Weather data unavailable");
            }

            return new WeatherResponse(
                    response.getCurrent().getTemperature2m(),
                    response.getCurrent().getWindSpeed10m(),
                    response.getCurrent().getWeatherCode()
            );

        } catch (RestClientException ex) {
            throw new ExternalApiException(
                    "Weather service is unavailable",
                    ex
            );
        }
    }

    private static class OpenMeteoResponse {

        private Current current;

        public Current getCurrent() {
            return current;
        }

        public void setCurrent(Current current) {
            this.current = current;
        }
    }

    private static class Current {

        private double temperature_2m;
        private double wind_speed_10m;
        private int weather_code;

        public double getTemperature2m() {
            return temperature_2m;
        }

        public double getWindSpeed10m() {
            return wind_speed_10m;
        }

        public int getWeatherCode() {
            return weather_code;
        }

        public void setTemperature_2m(double temperature_2m) {
            this.temperature_2m = temperature_2m;
        }

        public void setWind_speed_10m(double wind_speed_10m) {
            this.wind_speed_10m = wind_speed_10m;
        }

        public void setWeather_code(int weather_code) {
            this.weather_code = weather_code;
        }
    }
}