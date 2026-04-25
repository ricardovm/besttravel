package dev.ricardovm.besttravel.orchestratorservice.booking.dto;

public record AccommodationBookingRequest(
        String bookingId,
        String quoteId,
        AccommodationBookingCommandDTO accommodation) {
}
