package dev.ricardovm.besttravel.orchestratorservice.services.booking.dto;

public record AccommodationBookingRequest(
        String bookingId,
        String quoteId,
        AccommodationBookingCommandDTO accommodation) {
}
