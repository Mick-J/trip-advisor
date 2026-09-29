package com.mick.trip_advisor;

import com.mick.trip_advisor.dto.Accommodation;
import com.mick.trip_advisor.dto.FlightReservationRequest;
import com.mick.trip_advisor.dto.FlightReservationResponse;
import com.mick.trip_advisor.dto.Weather;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;
import java.util.List;

//@SpringBootTest
@Slf4j
class RestClientTests {

	@Test
	void simpleget() {
		var client = RestClient.create();
		var response = client.get()
				.uri("http://localhost:7070/sec02/weather/LAS")
				.retrieve()
				.body(Weather.class);
		log.info("response: {}", response);
	}

	@Test
	void baseUrl() {
		var client = RestClient.builder()
				.baseUrl("http://localhost:7070/sec02/weather/")
				.build();
		var response = client.get()
				.uri("{airport}", "LAS")
				.retrieve()
				.body(Weather.class);

	}

	@Test
	void listResponse() {
		var client = RestClient.builder()
				.baseUrl("http://localhost:7070/sec02/accommodations/")
				.build();
		var response = client.get()
				.uri("{airport}", "LAS")
				.retrieve()
				.body(new ParameterizedTypeReference<List<Accommodation>>() {
				});
		log.info("response: {}", response);
	}

	@Test
	void listRequesr() {
		var client = RestClient.builder()
				.baseUrl("http://localhost:7070/sec03/flight/reserve/")
				.build();
		var request = new FlightReservationRequest("ATL", "LAS", "UA789", LocalDate.now());
		var response = client.post()
				.body(request)
				.retrieve()
				.body(FlightReservationResponse.class);
		log.info("response: {}", response);
	}

}
