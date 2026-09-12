package com.planner.TourVista.Client.Impl;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import com.planner.TourVista.Client.RouteClient;
import com.planner.TourVista.Config.PlacesProperties;
import com.planner.TourVista.Exception.ExternalApiException;
import com.planner.TourVista.dto.RouteResponse;

@Component
public class GeoapifyRouteClient implements RouteClient {

    private final RestClient restClient;
    private final PlacesProperties placesProperties;

    public GeoapifyRouteClient(
            RestClient.Builder restClientBuilder,
            PlacesProperties placesProperties) {

        this.placesProperties = placesProperties;

        this.restClient = restClientBuilder
                .baseUrl(placesProperties.getBaseUrl())
                .build();
    }

    @Override
    public RouteResponse getRoute(
            double startLatitude,
            double startLongitude,
            double endLatitude,
            double endLongitude) {

        try {
            GeoapifyRouteResponse response = restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/v1/routing")
                            .queryParam(
                                    "waypoints",
                                    startLatitude + "," + startLongitude
                                            + "|" + endLatitude + "," + endLongitude
                            )
                            .queryParam("mode", "drive")
                            .queryParam("apiKey", placesProperties.getApiKey())
                            .build())
                    .retrieve()
                    .body(GeoapifyRouteResponse.class);

            if (response == null
                    || response.getFeatures() == null
                    || response.getFeatures().isEmpty()
                    || response.getFeatures().get(0).getProperties() == null) {

                throw new ExternalApiException("Route data unavailable");
            }

            var properties =
                    response.getFeatures().get(0).getProperties();

            return new RouteResponse(
                    properties.getDistance() / 1000.0,
                    properties.getTime() / 60.0
            );

        } catch (RestClientException ex) {

            throw new ExternalApiException(
                    "Routing service is unavailable",
                    ex
            );
        }
    }

    private static class GeoapifyRouteResponse {

        private java.util.List<Feature> features;

        public java.util.List<Feature> getFeatures() {
            return features;
        }

        public void setFeatures(java.util.List<Feature> features) {
            this.features = features;
        }
    }

    private static class Feature {

        private Properties properties;

        public Properties getProperties() {
            return properties;
        }

        public void setProperties(Properties properties) {
            this.properties = properties;
        }
    }

    private static class Properties {

        private double distance;
        private double time;

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }

        public double getTime() {
            return time;
        }

        public void setTime(double time) {
            this.time = time;
        }
    }
}