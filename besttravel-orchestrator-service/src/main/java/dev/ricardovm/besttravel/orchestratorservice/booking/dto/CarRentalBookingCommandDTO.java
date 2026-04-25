package dev.ricardovm.besttravel.orchestratorservice.booking.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CarRentalBookingCommandDTO(
        String id,
        String rentalCompany,
        String model,
        String pickupLocation,
        String dropOffLocation,
        LocalDate pickupDate,
        LocalDate dropOffDate,
        BigDecimal price) {
}
