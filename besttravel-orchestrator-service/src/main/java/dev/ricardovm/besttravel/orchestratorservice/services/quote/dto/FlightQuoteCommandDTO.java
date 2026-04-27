package dev.ricardovm.besttravel.orchestratorservice.services.quote.dto;

import java.time.LocalDate;

public record FlightQuoteCommandDTO(
        String origin,
        String destination,
        LocalDate departureDate,
        LocalDate returnDate) {
}
