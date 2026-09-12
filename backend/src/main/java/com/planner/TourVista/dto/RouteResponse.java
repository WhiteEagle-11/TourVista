package com.planner.TourVista.dto;

public class RouteResponse {

    private final double distanceKm;
    private final double durationMinutes;

    public RouteResponse(double distanceKm, double durationMinutes) {
        this.distanceKm = distanceKm;
        this.durationMinutes = durationMinutes;
    }

    public double getDistanceKm() {
        return distanceKm;
    }

    public double getDurationMinutes() {
        return durationMinutes;
    }
}