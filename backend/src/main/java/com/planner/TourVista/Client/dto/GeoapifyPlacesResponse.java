package com.planner.TourVista.Client.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class GeoapifyPlacesResponse {

    private List<Feature> features;

    public List<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }

    public static class Feature {

        private Properties properties;

        public Properties getProperties() {
            return properties;
        }

        public void setProperties(Properties properties) {
            this.properties = properties;
        }
    }

    public static class Properties {

        @JsonProperty("place_id")
        private String placeId;
        private String name;
        private String formatted;
        private double lat;
        private double lon;
        private List<String> categories;
        private double distance;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFormatted() {
            return formatted;
        }

        public void setFormatted(String formatted) {
            this.formatted = formatted;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLon() {
            return lon;
        }

        public void setLon(double lon) {
            this.lon = lon;
        }

        public List<String> getCategories() {
            return categories;
        }

        public void setCategories(List<String> categories) {
            this.categories = categories;
        }

        public String getPlaceId() {
            return placeId;
        }

        public void setPlaceId(String placeId) {
            this.placeId = placeId;
        }

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }
    }
}