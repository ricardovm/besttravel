package dev.ricardovm.besttravel.flightsservice.services.booking.dto.response;

public record BookingResponseDTO(
        String bookingId,
        FlightBookingResponseDTO flight,
        AccommodationBookingResponseDTO accommodation,
        CarRentalBookingResponseDTO carRental) {
    public static class Builder {
        private String bookingId;
        private FlightBookingResponseDTO flight;
        private AccommodationBookingResponseDTO accommodation;
        private CarRentalBookingResponseDTO carRental;

        public Builder bookingId(String bookingId) {
            this.bookingId = bookingId;
            return this;
        }

        public Builder flight(FlightBookingResponseDTO flight) {
            this.flight = flight;
            return this;
        }

        public Builder accommodation(AccommodationBookingResponseDTO accommodation) {
            this.accommodation = accommodation;
            return this;
        }

        public Builder carRental(CarRentalBookingResponseDTO carRental) {
            this.carRental = carRental;
            return this;
        }

        public BookingResponseDTO build() {
            return new BookingResponseDTO(bookingId, flight, accommodation, carRental);
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }
}
