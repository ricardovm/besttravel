package dev.ricardovm.besttravel.carrentalservice.services.booking.dto.response;

public record BookingResponseDTO(
        String bookingId,
        CarRentalBookingResponseDTO carRental) {
    public static class Builder {
        private String bookingId;
        private CarRentalBookingResponseDTO carRental;

        public Builder bookingId(String bookingId) {
            this.bookingId = bookingId;
            return this;
        }

        public Builder carRental(CarRentalBookingResponseDTO carRental) {
            this.carRental = carRental;
            return this;
        }

        public BookingResponseDTO build() {
            return new BookingResponseDTO(bookingId, carRental);
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }
}
