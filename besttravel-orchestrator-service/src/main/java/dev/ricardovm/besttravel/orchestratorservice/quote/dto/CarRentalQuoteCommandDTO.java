package dev.ricardovm.besttravel.orchestratorservice.quote.dto;

import java.time.LocalDate;

public record CarRentalQuoteCommandDTO(
        String pickupLocation,
        String dropOffLocation,
        LocalDate pickupDate,
        LocalDate dropOffDate) {
}
