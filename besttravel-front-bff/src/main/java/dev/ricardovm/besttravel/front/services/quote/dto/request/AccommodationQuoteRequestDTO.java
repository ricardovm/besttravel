package dev.ricardovm.besttravel.front.services.quote.dto.request;

import java.time.LocalDate;

public record AccommodationQuoteRequestDTO(
        String destination,
        LocalDate checkInDate,
        LocalDate checkOutDate) {
}
