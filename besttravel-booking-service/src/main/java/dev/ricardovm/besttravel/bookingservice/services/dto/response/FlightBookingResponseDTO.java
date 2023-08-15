package dev.ricardovm.besttravel.bookingservice.services.dto.response;

public record FlightBookingResponseDTO(
        String bookingId,
        String flightId,
        String status) {
}
