package dev.ricardovm.besttravel.bookingservice.services.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CarRentalBookingRequestDTO(
        String id,
        String rentalCompany,
        String model,
        String pickupLocation,
        String dropOffLocation,
        LocalDate pickupDate,
        LocalDate dropOffDate,
        BigDecimal price) {
}
