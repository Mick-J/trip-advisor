package com.mick.trip_advisor.service;


import com.mick.trip_advisor.client.*;
import com.mick.trip_advisor.dto.TripPlan;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
@Service
public class TripPlanService {

    private final EventServiceClient eventServiceClient;
    private final WeatherServiceClient weatherServiceClient;
    private final AccommodationServiceClient accommodationServiceClient;
    private final TransportationServiceClient transportationServiceClient;
    private final LocalRecommendationServiceClient localRecommendationServiceClient;
    private final ExecutorService executors;

    public TripPlanService(EventServiceClient eventServiceClient,
                           WeatherServiceClient weatherServiceClient,
                           AccommodationServiceClient accommodationServiceClient,
                           TransportationServiceClient transportationServiceClient,
                           LocalRecommendationServiceClient localRecommendationServiceClient,
                           ExecutorService executors) {
        this.eventServiceClient = eventServiceClient;
        this.weatherServiceClient = weatherServiceClient;
        this.accommodationServiceClient = accommodationServiceClient;
        this.transportationServiceClient = transportationServiceClient;
        this.localRecommendationServiceClient = localRecommendationServiceClient;
        this.executors = executors;
    }

    public TripPlan getTripPlan(String airportCode) {
        var events = this.executors.submit(() -> this.eventServiceClient.getEvents(airportCode));
        var weather = this.executors.submit(() -> this.weatherServiceClient.getWeather(airportCode));
        var accommodations = this.executors.submit(() -> this.accommodationServiceClient.getAccommodations(airportCode));
        var transportations = this.executors.submit(() -> this.transportationServiceClient.getTransportation(airportCode));
        var recommendations = this.executors.submit(() -> this.localRecommendationServiceClient.getRecommendations(airportCode));

        return new TripPlan(
                airportCode,
                getOrElse(accommodations, Collections.emptyList()),
                getOrElse(weather, null),
                getOrElse(events, Collections.emptyList()),
                getOrElse(recommendations, null),
                getOrElse(transportations, null)
        );
    }

    private <T> T getOrElse(Future<T> future, T defaultValue) {
        try {
            return future.get();
        } catch (Exception e) {
            log.error("error", e);
        }
        return defaultValue;
    }
}
