package dev.ricardovm.besttravel.orchestratorservice.booking.dto;

public record FlightBookingRequest(
        String bookingId,
        String quoteId,
        FlightBookingCommandDTO flight) {
}
