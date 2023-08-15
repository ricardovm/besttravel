package dev.ricardovm.besttravel.flightsservice.services.booking.dto.response;

public record CarRentalBookingResponseDTO(
        String bookingId,
        String carRentalId,
        String status) {
    public static class Builder {
        private String bookingId;
        private String carRentalId;
        private String status;

        public Builder bookingId(String bookingId) {
            this.bookingId = bookingId;
            return this;
        }

        public Builder carRentalId(String carRentalId) {
            this.carRentalId = carRentalId;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public CarRentalBookingResponseDTO build() {
            return new CarRentalBookingResponseDTO(bookingId, carRentalId, status);
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }
}
