package com.planner.TourVista.dto;

public class Recommendation {

    private final PlaceResponse place;
    private final double score;
    private final String reason;

    public Recommendation(
            PlaceResponse place,
            double score,
            String reason) {

        this.place = place;
        this.score = score;
        this.reason = reason;
    }

    public PlaceResponse getPlace() {
        return place;
    }

    public double getScore() {
        return score;
    }

    public String getReason() {
        return reason;
    }
}