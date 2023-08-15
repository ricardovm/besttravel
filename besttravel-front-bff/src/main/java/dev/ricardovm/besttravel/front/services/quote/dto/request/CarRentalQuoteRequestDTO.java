package dev.ricardovm.besttravel.front.services.quote.dto.request;

import java.time.LocalDate;

public record CarRentalQuoteRequestDTO(
        String pickupLocation,
        String dropOffLocation,
        LocalDate pickupDate,
        LocalDate dropOffDate) {
}
