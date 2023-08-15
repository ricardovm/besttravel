package dev.ricardovm.besttravel.carrentalservice.services.booking.dto.request;

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
