package dev.ricardovm.besttravel.front.services.booking.dto.response;

public record FlightBookingResponseDTO(
        String bookingId,
        String flightId,
        String status) {
}
