package com.mick.trip_advisor.dto;

import java.time.LocalDate;

public record FlightReservationRequest(String departure,
                                       String arrival,
                                       String flightNumber,
                                       LocalDate tripDate) {
}
