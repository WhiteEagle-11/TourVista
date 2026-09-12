package com.planner.TourVista.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class CreateTripRequest {

    @NotBlank(message = "Destination is required")
    private String destination;

    @Positive(message = "Days must be greater than 0")
    private int days;
    
    @Positive(message = "Budget must be greater than 0")
    private double budget;

    private List<String> interests;

    public String getDestination(){
        return destination;
    }
    public void setDestination(String destination){
        this.destination = destination;
    }
    public int getDays(){
        return days;
    }
    public void setDays(int days){
        this.days = days;
    }
    public double getBudget(){
        return budget;
    }
    public void setBudget(double budget){
        this.budget = budget;
    }

    public List getInterests() {
        return interests;
    }

    public void setInterests(List interests) {
        this.interests = interests;
    }
    
}
