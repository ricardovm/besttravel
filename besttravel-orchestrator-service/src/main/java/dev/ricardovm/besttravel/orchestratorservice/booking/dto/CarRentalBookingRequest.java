package dev.ricardovm.besttravel.orchestratorservice.booking.dto;

public record CarRentalBookingRequest(
        String bookingId,
        String quoteId,
        CarRentalBookingCommandDTO carRental) {
}
