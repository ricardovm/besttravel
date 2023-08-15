package dev.ricardovm.besttravel.flightsservice.services.quote.dto.request;

import java.time.LocalDate;

public record FlightQuoteRequestDTO(
        String origin,
        String destination,
        LocalDate departureDate,
        LocalDate returnDate) {

}
