package dev.ricardovm.besttravel.orchestratorservice.booking.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AccommodationBookingCommandDTO(
        String id,
        String hotel,
        String city,
        LocalDate checkInDate,
        LocalDate checkOutDate,
        BigDecimal price) {
}
