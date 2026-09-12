package com.planner.TourVista.dto;
import java.util.List;

public class TripResponse {

    private Long id;
    private String destination;
    private int days;
    private double budget;
    private List<String> interests;

    public TripResponse(Long id, String destination, int days, double budget, List<String> interests) {
        this.id = id;
        this.destination = destination;
        this.days = days;
        this.budget = budget;
        this.interests= interests;
    }

    public Long getId() {
        return id;
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


}