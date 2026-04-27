package dev.ricardovm.besttravel.orchestratorservice.services.quote.dto;

import java.time.LocalDate;

public record AccommodationQuoteCommandDTO(
        String destination,
        LocalDate checkInDate,
        LocalDate checkOutDate) {
}
