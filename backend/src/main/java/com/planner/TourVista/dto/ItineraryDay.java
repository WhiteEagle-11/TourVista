package com.planner.TourVista.dto;

import java.util.List;

public class ItineraryDay {

    private final int day;
    private final List<Recommendation> recommendations;

    public ItineraryDay(
            int day,
            List<Recommendation> recommendations) {

        this.day = day;
        this.recommendations = recommendations;
    }

    public int getDay() {
        return day;
    }

    public List<Recommendation> getRecommendations() {
        return recommendations;
    }
}