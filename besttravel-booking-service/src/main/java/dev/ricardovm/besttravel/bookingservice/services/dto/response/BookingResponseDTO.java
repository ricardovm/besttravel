package dev.ricardovm.besttravel.bookingservice.services.dto.response;

public record BookingResponseDTO(
        String bookingId,
        FlightBookingResponseDTO flight,
        AccommodationBookingResponseDTO accommodation,
        CarRentalBookingResponseDTO carRental) {
}
