package dev.ricardovm.besttravel.orchestratorservice.quote.dto;

import java.time.LocalDate;

public record FlightQuoteCommandDTO(
        String origin,
        String destination,
        LocalDate departureDate,
        LocalDate returnDate) {
}
