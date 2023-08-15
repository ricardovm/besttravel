package dev.ricardovm.besttravel.bookingservice.services.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

public record FlightBookingRequestDTO(
        String id,
        String flighCompany,
        String origin,
        String destination,
        LocalDate departureDate,
        LocalDate returnDate,
        BigDecimal price) {
}
