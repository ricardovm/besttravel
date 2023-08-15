package dev.ricardovm.besttravel.flightsservice.services.booking.dto.request;

public record BookingRequestDTO(
        String bookingId,
        String quoteId,
        FlightBookingRequestDTO flight,
        AccommodationBookingRequestDTO accommodation,
        CarRentalBookingRequestDTO carRental) {
}

