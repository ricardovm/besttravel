package dev.ricardovm.besttravel.orchestratorservice.services.booking.dto;

public record CarRentalBookingRequest(
        String bookingId,
        String quoteId,
        CarRentalBookingCommandDTO carRental) {
}
