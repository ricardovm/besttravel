package dev.ricardovm.besttravel.orchestratorservice.booking.dto;

public record BookingCommandDTO(
        String bookingId,
        String quoteId,
        FlightBookingCommandDTO flight,
        AccommodationBookingCommandDTO accommodation,
        CarRentalBookingCommandDTO carRental) {
}
