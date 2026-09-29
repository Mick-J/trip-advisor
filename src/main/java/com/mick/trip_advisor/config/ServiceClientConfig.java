package com.mick.trip_advisor.config;

import com.mick.trip_advisor.client.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.net.http.HttpClient;
import java.util.concurrent.Executors;

@Slf4j
@Configuration
public class ServiceClientConfig {

    @Value("${spring.threads.virtual.enabled}")
    private boolean isVirtualEnabled;

    @Bean
    public AccommodationServiceClient accommodationServiceClient(@Value("${accommodation.service.url}") String baseUrl) {
        return new AccommodationServiceClient(builRestClient(baseUrl));
    }

    @Bean
    public EventServiceClient eventServiceClient(@Value("${event.service.url}") String baseUrl) {
        return new EventServiceClient(builRestClient(baseUrl));
    }

    @Bean
    public WeatherServiceClient weatherServiceClient(@Value("${weather.service.url}") String baseUrl) {
        return new WeatherServiceClient(builRestClient(baseUrl));
    }

    @Bean
    public TransportationServiceClient transportationServiceClient(@Value("${transportation.service.url}") String baseUrl) {
        return new TransportationServiceClient(builRestClient(baseUrl));
    }

    @Bean
    public LocalRecommendationServiceClient recommendationServiceClient(@Value("${local-recommendation.service.url}") String baseUrl) {
        return new LocalRecommendationServiceClient(builRestClient(baseUrl));
    }

    @Bean
    public FlightSearchServiceClient flightSearchServiceClient(@Value("${flight-search.service.url}") String baseUrl) {
        return new FlightSearchServiceClient(builRestClient(baseUrl));
    }

    @Bean
    public FlightReservationServiceClient reservationServiceClient(@Value("${flight-reservation.service.url}") String baseUrl) {
        return new FlightReservationServiceClient(builRestClient(baseUrl));
    }

    private RestClient builRestClient(String baseUrl) {
        log.info("base url: {}", baseUrl);
        var builder = RestClient.builder().baseUrl(baseUrl);
        if (isVirtualEnabled) {
            builder = builder.requestFactory(new JdkClientHttpRequestFactory(
                    HttpClient.newBuilder().executor(Executors.newVirtualThreadPerTaskExecutor()).build()
            ));
        }
        return builder.build();
    }
}
