package com.planner.TourVista.Entity;

import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity 
public class Trip {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String destination;
    private int days;
    private double budget;

    @ElementCollection 
    private List<String> interests;

    public Long getId() {
        return id;
    }
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

    public List<String> getInterests() {
        return interests;
    }

    public void setInterests(List<String> interests) {
        this.interests = interests;
    }

    
}
