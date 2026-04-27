package dev.ricardovm.besttravel.orchestratorservice.services.booking.dto;

public record FlightBookingRequest(
        String bookingId,
        String quoteId,
        FlightBookingCommandDTO flight) {
}
