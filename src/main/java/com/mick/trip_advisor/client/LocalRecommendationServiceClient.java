package com.mick.trip_advisor.client;

import com.mick.trip_advisor.dto.LocalRecommendations;
import org.springframework.web.client.RestClient;

public class LocalRecommendationServiceClient {

    private final RestClient client;

    public LocalRecommendationServiceClient(RestClient client) {
        this.client = client;
    }

    public LocalRecommendations getRecommendations(String airportCode) {
        return this.client.get()
                .uri("{airportCode}", airportCode)
                .retrieve()
                .body(LocalRecommendations.class);

    }
}
