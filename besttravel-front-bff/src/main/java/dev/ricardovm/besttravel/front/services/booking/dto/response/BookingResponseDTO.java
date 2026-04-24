package dev.ricardovm.besttravel.front.services.booking.dto.response;

public record BookingResponseDTO(
        String bookingId,
        FlightBookingResponseDTO flight,
        AccommodationBookingResponseDTO accommodation,
        CarRentalBookingResponseDTO carRental,
        Boolean timedOut) {
    public static BookingResponseDTO timeout(String bookingId) {
        return new BookingResponseDTO(bookingId, null, null, null, true);
    }
}
