package dev.ricardovm.besttravel.orchestratorservice.services.quote.dto;

import java.time.LocalDate;

public record CarRentalQuoteCommandDTO(
        String pickupLocation,
        String dropOffLocation,
        LocalDate pickupDate,
        LocalDate dropOffDate) {
}
