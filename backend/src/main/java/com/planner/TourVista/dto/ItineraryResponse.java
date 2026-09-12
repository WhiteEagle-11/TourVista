package com.planner.TourVista.dto;

import java.util.List;

public class ItineraryResponse {

    private final List<ItineraryDay> days;

    public ItineraryResponse(List<ItineraryDay> days) {
        this.days = days;
    }

    public List<ItineraryDay> getDays() {
        return days;
    }
}