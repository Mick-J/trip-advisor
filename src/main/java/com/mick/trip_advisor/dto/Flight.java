package com.mick.trip_advisor.dto;

import java.time.LocalDate;

public record Flight(String flightNumber,
                     String airline,
                     int price,
                     LocalDate tripDate,
                     int flightDurationInMinutes) {
}
