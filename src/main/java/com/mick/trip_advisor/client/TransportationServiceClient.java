package com.mick.trip_advisor.client;

import com.mick.trip_advisor.dto.Transportation;
import org.springframework.web.client.RestClient;

public class TransportationServiceClient {

    private final RestClient client;

    public TransportationServiceClient(RestClient client) {
        this.client = client;
    }

    public Transportation getTransportation(String airportCode) {
        return this.client.get()
                .uri("{airportCode}", airportCode)
                .retrieve()
                .body(Transportation.class);
    }
}
