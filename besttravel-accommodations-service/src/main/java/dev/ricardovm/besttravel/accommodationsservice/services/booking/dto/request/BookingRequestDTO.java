package dev.ricardovm.besttravel.accommodationsservice.services.booking.dto.request;

public record BookingRequestDTO(
        String bookingId,
        String quoteId,
        AccommodationBookingRequestDTO accommodation) {
}

