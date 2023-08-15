package dev.ricardovm.besttravel.bookingservice.services.dto.request;

public record BookingRequestDTO(
        String bookingId,
        String quoteId,
        FlightBookingRequestDTO flight,
        AccommodationBookingRequestDTO accommodation,
        CarRentalBookingRequestDTO carRental) {
}

