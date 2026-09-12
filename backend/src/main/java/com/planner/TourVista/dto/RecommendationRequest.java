package com.planner.TourVista.dto;

import java.util.List;

public class RecommendationRequest {

    private String destination;
    private int days;
    private double budget;
    private List<String> interests;
    private List<PlaceResponse> places;
    private WeatherResponse weather;
    private Double destinationLatitude;
    private Double destinationLongitude;

    public RecommendationRequest(
            String destination,
            int days,
            double budget,
            List<String> interests,
            List<PlaceResponse> places,
            WeatherResponse weather,
            Double destinationLatitude,
            Double destinationLongitude) {

        this.destination = destination;
        this.days = days;
        this.budget = budget;
        this.interests = interests;
        this.places = places;
        this.weather = weather;
        this.destinationLatitude = destinationLatitude;
        this.destinationLongitude = destinationLongitude;
    }

    public String getDestination() {
        return destination;
    }

    public int getDays() {
        return days;
    }

    public double getBudget() {
        return budget;
    }

    public List<String> getInterests() {
        return interests;
    }

    public List<PlaceResponse> getPlaces() {
        return places;
    }

    public WeatherResponse getWeather() {
        return weather;
    }

    public Double getDestinationLatitude() {
        return destinationLatitude;
    }

    public Double getDestinationLongitude() {
        return destinationLongitude;
    }
}