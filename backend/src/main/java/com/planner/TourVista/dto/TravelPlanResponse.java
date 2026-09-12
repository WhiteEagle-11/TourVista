package com.planner.TourVista.dto;

public class TravelPlanResponse {

    private final ItineraryResponse itinerary;
    private final String aiPlan;

    public TravelPlanResponse(ItineraryResponse itinerary,
        String aiPlan
    ) {
        this.itinerary = itinerary;
        this.aiPlan = aiPlan;
    }

    public ItineraryResponse getItinerary() {
        return itinerary;
    }

    public String getAiPlan() {
        return aiPlan;
    }
}