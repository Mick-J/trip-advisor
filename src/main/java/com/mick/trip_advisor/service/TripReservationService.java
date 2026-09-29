package com.mick.trip_advisor.service;


import com.mick.trip_advisor.client.FlightReservationServiceClient;
import com.mick.trip_advisor.client.FlightSearchServiceClient;
import com.mick.trip_advisor.dto.Flight;
import com.mick.trip_advisor.dto.FlightReservationRequest;
import com.mick.trip_advisor.dto.FlightReservationResponse;
import com.mick.trip_advisor.dto.TripReservationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Slf4j
@Service
public class TripReservationService {

    private final FlightSearchServiceClient searchServiceClient;
    private final FlightReservationServiceClient reservationServiceClient;


    public TripReservationService(FlightSearchServiceClient searchServiceClient, FlightReservationServiceClient reservationServiceClient) {
        this.searchServiceClient = searchServiceClient;
        this.reservationServiceClient = reservationServiceClient;
    }

    public FlightReservationResponse reserve(TripReservationRequest request) {
        var flights = this.searchServiceClient.getFlights(request.departure(), request.arrival());
        var bestDeal = flights.stream().min(Comparator.comparingInt(Flight::price));
        var flight = bestDeal.orElseThrow(() -> new IllegalArgumentException("no Flights found"));
        var reservationRequest = new FlightReservationRequest(request.departure(), request.arrival(), flight.flightNumber(), request.date());
        return this.reservationServiceClient.reserve(reservationRequest);
    }
}
