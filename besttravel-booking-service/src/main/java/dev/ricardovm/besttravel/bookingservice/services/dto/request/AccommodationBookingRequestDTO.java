package dev.ricardovm.besttravel.bookingservice.services.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AccommodationBookingRequestDTO(
        String id,
        String hotel,
        String city,
        LocalDate checkInDate,
        LocalDate checkOutDate,
        BigDecimal price) {
}
