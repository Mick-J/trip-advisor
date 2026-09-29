package com.mick.trip_advisor.controller;

import com.mick.trip_advisor.dto.FlightReservationResponse;
import com.mick.trip_advisor.dto.TripPlan;
import com.mick.trip_advisor.dto.TripReservationRequest;
import com.mick.trip_advisor.service.TripPlanService;
import com.mick.trip_advisor.service.TripReservationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("trip")
public class TripController {

    private final TripPlanService planService;
    private final TripReservationService reservationService;

    public TripController(TripPlanService planService, TripReservationService reservationService) {
        this.planService = planService;
        this.reservationService = reservationService;
    }

    @GetMapping("{airportCode}")
    public TripPlan planTrip(@PathVariable("airportCode") String airportCode) {
//         log.info("airport code: {}, is virtual: {}", airportCode, Thread.currentThread().isVirtual());
        return this.planService.getTripPlan(airportCode);
    }

    @PostMapping("reserve")
    public FlightReservationResponse reserveFlight(@RequestBody TripReservationRequest request) {
        return this.reservationService.reserve(request);
    }
}
