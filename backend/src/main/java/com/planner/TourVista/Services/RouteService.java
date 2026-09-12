package com.planner.TourVista.Services;

import org.springframework.stereotype.Service;

import com.planner.TourVista.Client.RouteClient;
import com.planner.TourVista.dto.RouteResponse;

@Service
public class RouteService {

    private final RouteClient routeClient;

    public RouteService(RouteClient routeClient) {
        this.routeClient = routeClient;
    }

    public RouteResponse getRoute(
            double startLatitude,
            double startLongitude,
            double endLatitude,
            double endLongitude) {

        return routeClient.getRoute(
                startLatitude,
                startLongitude,
                endLatitude,
                endLongitude
        );
    }
}