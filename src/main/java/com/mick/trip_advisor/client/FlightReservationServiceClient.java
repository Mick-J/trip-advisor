package com.mick.trip_advisor.client;

import com.mick.trip_advisor.dto.FlightReservationRequest;
import com.mick.trip_advisor.dto.FlightReservationResponse;
import org.springframework.web.client.RestClient;

public class FlightReservationServiceClient {
    private final RestClient client;

    public FlightReservationServiceClient(RestClient client) {
        this.client = client;
    }

    public FlightReservationResponse reserve(FlightReservationRequest request) {
        return this.client.post()
                .body(request)
                .retrieve()
                .body(FlightReservationResponse.class);
    }
}
