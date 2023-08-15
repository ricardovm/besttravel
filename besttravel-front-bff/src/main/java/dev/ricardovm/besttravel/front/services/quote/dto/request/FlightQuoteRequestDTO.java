package dev.ricardovm.besttravel.front.services.quote.dto.request;

import java.time.LocalDate;

public record FlightQuoteRequestDTO(
        String origin,
        String destination,
        LocalDate departureDate,
        LocalDate returnDate) {

}
