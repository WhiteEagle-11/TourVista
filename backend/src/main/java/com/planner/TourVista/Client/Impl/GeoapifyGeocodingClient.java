package com.planner.TourVista.Client.Impl;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import com.planner.TourVista.Client.GeocodingClient;
import com.planner.TourVista.Client.dto.GeoapifyGeocodingResponse;
import com.planner.TourVista.Config.PlacesProperties;
import com.planner.TourVista.Exception.ExternalApiException;
import com.planner.TourVista.dto.GeoCoordinates;

@Component
public class GeoapifyGeocodingClient implements GeocodingClient {

    private final RestClient restClient;
    private final PlacesProperties placesProperties;

    public GeoapifyGeocodingClient(
            RestClient.Builder restClientBuilder,
            PlacesProperties placesProperties) {

        this.placesProperties = placesProperties;

        this.restClient = restClientBuilder
                .baseUrl(placesProperties.getBaseUrl())
                .build();
    }

   @Override
public GeoCoordinates geocode(String destination) {

    try {
        GeoapifyGeocodingResponse response = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/geocode/search")
                        .queryParam("text", destination)
                        .queryParam("apiKey", placesProperties.getApiKey())
                        .queryParam("format", "json")
                        .build())
                .retrieve()
                .body(GeoapifyGeocodingResponse.class);

        if (response == null
                || response.getResults() == null
                || response.getResults().isEmpty()) {

            throw new ExternalApiException(
                    "Destination could not be found"
            );
        }

        var result = response.getResults().get(0);

        return new GeoCoordinates(
                result.getLat(),
                result.getLon()
        );

    } catch (RestClientException ex) {

        throw new ExternalApiException(
                "Geocoding service is unavailable",
                ex
        );
    }
}
}