package dev.ricardovm.besttravel.front.services.booking.dto.request;

public record BookingRequestDTO(
        String bookingId,
        String quoteId,
        FlightBookingRequestDTO flight,
        AccommodationBookingRequestDTO accommodation,
        CarRentalBookingRequestDTO carRental
) {
    public static class Builder {
        private String bookingId;
        private String quoteId;
        private FlightBookingRequestDTO flight;
        private AccommodationBookingRequestDTO accommodation;
        private CarRentalBookingRequestDTO carRental;

        Builder() {
        }

        public Builder bookingId(String bookingId) {
            this.bookingId = bookingId;
            return this;
        }

        public Builder quoteId(String quoteId) {
            this.quoteId = quoteId;
            return this;
        }

        public Builder flight(FlightBookingRequestDTO flight) {
            this.flight = flight;
            return this;
        }

        public Builder accommodation(AccommodationBookingRequestDTO accommodation) {
            this.accommodation = accommodation;
            return this;
        }

        public Builder carRental(CarRentalBookingRequestDTO carRental) {
            this.carRental = carRental;
            return this;
        }

        public BookingRequestDTO build() {
            return new BookingRequestDTO(bookingId, quoteId, flight, accommodation, carRental);
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }
}
