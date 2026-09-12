package com.planner.TourVista.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.planner.TourVista.Client.GeocodingClient;
import com.planner.TourVista.Client.PlacesClient;
import com.planner.TourVista.Client.Qwen3Client;
import com.planner.TourVista.Client.WeatherClient;
import com.planner.TourVista.dto.ItineraryResponse;
import com.planner.TourVista.dto.Recommendation;
import com.planner.TourVista.dto.RecommendationRequest;
import com.planner.TourVista.dto.TravelPlanResponse;

@Service
public class TravelPlanService {

    private final RecommendationService recommendationService;
    private final ItineraryService itineraryService;
    private final Qwen3Client qwen3Client;
    private final GeocodingClient geocodingClient;
    private final PlacesClient placesClient;
    private final WeatherClient weatherClient;
    

    public TravelPlanService(
            RecommendationService recommendationService,
            ItineraryService itineraryService,
            Qwen3Client qwen3Client,
            GeocodingClient geocodingClient,
            PlacesClient placesClient,
            WeatherClient weatherClient
            ) {

        this.recommendationService = recommendationService;
        this.itineraryService = itineraryService;
        this.qwen3Client = qwen3Client;
        this.geocodingClient = geocodingClient;
        this.placesClient = placesClient;
        this.weatherClient = weatherClient;
    }

    public TravelPlanResponse generatePlan(
        RecommendationRequest request) {

    var coordinates =
        geocodingClient.geocode(request.getDestination());

    var places =
        placesClient.searchPlaces(request.getDestination());

    var weather =
        weatherClient.getWeather(
                coordinates.getLatitude(),
                coordinates.getLongitude()
        );

    RecommendationRequest enrichedRequest =
        new RecommendationRequest(
                request.getDestination(),
                request.getDays(),
                request.getBudget(),
                request.getInterests(),
                places,
                weather,
                coordinates.getLatitude(),
                coordinates.getLongitude()
        );

    List<Recommendation> recommendations =
        recommendationService.recommend(enrichedRequest);

    ItineraryResponse itinerary =
            itineraryService.optimize(
                    recommendations,
                    request.getDays()
            );

    StringBuilder itineraryText = new StringBuilder();

itineraryText.append("Destination: ")
        .append(request.getDestination())
        .append("\n");

itineraryText.append("Trip duration: ")
        .append(request.getDays())
        .append(" days\n");

itineraryText.append("Budget: ")
        .append(request.getBudget())
        .append("\n");

itineraryText.append("Interests: ")
        .append(request.getInterests())
        .append("\n");

if (weather != null) {
    itineraryText.append("Weather: ")
            .append(weather.getTemperature())
            .append("°C, wind ")
            .append(weather.getWindSpeed())
            .append(" km/h, weather code ")
            .append(weather.getWeatherCode())
            .append("\n");
}

itineraryText.append("\nOptimized itinerary:\n");

for (var day : itinerary.getDays()) {

    itineraryText.append("Day ")
            .append(day.getDay())
            .append(":\n");

    if (day.getRecommendations().isEmpty()) {
        itineraryText.append("- No attractions scheduled\n");
        continue;
    }

    for (Recommendation recommendation : day.getRecommendations()) {

        itineraryText.append("- ")
                .append(recommendation.getPlace().getName())
                .append(" (")
                .append(recommendation.getPlace().getAddress())
                .append(")")
                .append(" | Score: ")
                .append(recommendation.getScore())
                .append(" | ")
                .append(recommendation.getReason())
                .append("\n");
    }
}

String prompt = """
        Create a practical travel plan using ONLY the information provided below.

        Rules:
        - Respect the exact trip duration.
        - Use only attractions present in the optimized itinerary.
        - Do not invent attractions, restaurants, hotels, prices, opening hours,
          ticket availability, reservations, or exact travel times.
        - Do not contradict the supplied weather information.
        - Do not claim that something was booked or reserved.
        - If there are fewer attractions than trip days, describe the remaining
          days as free time or rest days.
        - Keep the plan concise and useful.
        - Organize the response by day.

        Travel information:
        """ + itineraryText;

String aiPlan =
        qwen3Client.generateTravelPlan(prompt);

   

    return new TravelPlanResponse(
            itinerary,
            aiPlan
    );
}
}