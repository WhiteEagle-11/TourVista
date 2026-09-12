package com.planner.TourVista.Client.Impl;

import java.net.URI;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import com.planner.TourVista.Client.GeocodingClient;
import com.planner.TourVista.Client.PlacesClient;
import com.planner.TourVista.Client.dto.GeoapifyPlacesResponse;
import com.planner.TourVista.Config.PlacesProperties;
import com.planner.TourVista.dto.GeoCoordinates;
import com.planner.TourVista.dto.PlaceResponse;


@Component
public class GeoapifyPlacesClient implements PlacesClient {

    private final RestClient restClient;
    private final PlacesProperties placesProperties;
    private final GeocodingClient geocodingClient;

    public GeoapifyPlacesClient(
            RestClient.Builder restClientBuilder,
            PlacesProperties placesProperties,
            GeocodingClient geocodingClient) {

        this.placesProperties = placesProperties;
        this.geocodingClient = geocodingClient;

        this.restClient = restClientBuilder
                .baseUrl(placesProperties.getBaseUrl())
                .build();
    }

    @Override
public List<PlaceResponse> searchPlaces(String destination) {

    GeoCoordinates coordinates =
            geocodingClient.geocode(destination);

    URI uri = UriComponentsBuilder
        .fromUriString(placesProperties.getBaseUrl())
        .path("/v2/places")
        .queryParam("categories", "tourism.sights")
        .queryParam(
                "filter",
                "circle:"
                        + coordinates.getLongitude()
                        + ","
                        + coordinates.getLatitude()
                        + ",5000"
        )
        .queryParam("limit", 10)
        .queryParam("apiKey", placesProperties.getApiKey())
        .build()
        .toUri();

GeoapifyPlacesResponse response = restClient.get()
        .uri(uri)
        .retrieve()
        .body(GeoapifyPlacesResponse.class);

    if (response == null || response.getFeatures() == null) {
        return List.of();
    }

    return response.getFeatures()
            .stream()
            .map(feature -> new PlaceResponse(
                    feature.getProperties().getPlaceId(),
                    feature.getProperties().getName(),
                    feature.getProperties().getFormatted(),
                    feature.getProperties().getLat(),
                    feature.getProperties().getLon(),
                    0.0,
                    feature.getProperties().getDistance(),
                    feature.getProperties().getCategories()
                    
            ))
            .toList();
}
}