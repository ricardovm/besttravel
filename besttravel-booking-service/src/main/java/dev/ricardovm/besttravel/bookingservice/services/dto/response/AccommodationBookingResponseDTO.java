package dev.ricardovm.besttravel.bookingservice.services.dto.response;

public record AccommodationBookingResponseDTO(
        String bookingId,
        String accommodationId,
        String status) {
}
