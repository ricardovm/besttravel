package dev.ricardovm.besttravel.orchestratorservice.services.booking.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record FlightBookingCommandDTO(
        String id,
        String flightCompany,
        String origin,
        String destination,
        LocalDate departureDate,
        LocalDate returnDate,
        BigDecimal price) {
}
