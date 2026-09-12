package com.planner.TourVista.Client;

import com.planner.TourVista.dto.RouteResponse;

public interface RouteClient {

    RouteResponse getRoute(
            double startLatitude,
            double startLongitude,
            double endLatitude,
            double endLongitude);
}