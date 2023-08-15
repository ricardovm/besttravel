package dev.ricardovm.besttravel.carrentalservice.services.booking.dto.request;

public record BookingRequestDTO(
        String bookingId,
        String quoteId,
        CarRentalBookingRequestDTO carRental) {
}

