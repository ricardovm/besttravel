package dev.ricardovm.besttravel.carrentalservice.services.quote.dto.request;

import java.time.LocalDate;

public record CarRentalQuoteRequestDTO(
        String pickupLocation,
        String dropOffLocation,
        LocalDate pickupDate,
        LocalDate dropOffDate) {
}
