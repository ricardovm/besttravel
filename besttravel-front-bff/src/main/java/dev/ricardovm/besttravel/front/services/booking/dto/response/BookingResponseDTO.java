package dev.ricardovm.besttravel.front.services.booking.dto.response;

public record BookingResponseDTO(
        String bookingId,
        FlightBookingResponseDTO flight,
        AccommodationBookingResponseDTO accommodation,
        CarRentalBookingResponseDTO carRental) {
}
