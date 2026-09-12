package com.planner.TourVista.Client.Impl;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.planner.TourVista.Client.Qwen3Client;
import com.planner.TourVista.Config.QwenProperties;
import com.planner.TourVista.Exception.ExternalApiException;

@Component
public class OllamaQwen3Client implements Qwen3Client {

    private final RestClient restClient;
    private final QwenProperties qwenProperties;

    public OllamaQwen3Client(
            RestClient.Builder restClientBuilder,
            QwenProperties qwenProperties) {

        this.qwenProperties = qwenProperties;

        this.restClient = restClientBuilder
                .baseUrl(qwenProperties.getBaseUrl())
                .build();
    }

    @Override
    public String generateTravelPlan(String itinerary) {

        try {

            OllamaRequest request = new OllamaRequest(
                    qwenProperties.getModel(),
                    "Create a concise, practical travel plan from this itinerary:\n"
                            + itinerary,
                    false
            );

            OllamaResponse response = restClient.post()
                    .uri("/api/generate")
                    .body(request)
                    .retrieve()
                    .body(OllamaResponse.class);

            if (response == null || response.getResponse() == null) {
                throw new ExternalApiException(
                        "Qwen3 returned an empty response"
                );
            }

            return response.getResponse();

        } catch (Exception ex) {

           if (ex instanceof ExternalApiException) {
    throw (ExternalApiException) ex;
}

            throw new ExternalApiException(
                    "Qwen3 service is unavailable",
                    ex
            );
        }
    }

    private record OllamaRequest(
            String model,
            String prompt,
            boolean stream) {
    }

    private static class OllamaResponse {

        private String response;

        public String getResponse() {
            return response;
        }

        public void setResponse(String response) {
            this.response = response;
        }
    }
}