package com.planner.TourVista.dto;
import java.util.List;

public class PlaceResponse {

    private String name;
    private String address;
    private double latitude;
    private double longitude;
    private double rating;
    private List<String> categories;
    private String placeId;
    private double distance;

    public PlaceResponse(
            String placeId,
            String name,
            String address,
            double latitude,
            double longitude,
            double rating,
            double distance,
            List<String> categories
        ) {

        this.placeId = placeId;
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.rating = rating;
        this.distance = distance;
        this.categories = categories;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getRating() {
        return rating;
    }

    public List<String> getCategories() {
        return categories;
    }

    public String getPlaceId() {
        return placeId;
    }

    public double getDistance() {
        return distance;
    }
}