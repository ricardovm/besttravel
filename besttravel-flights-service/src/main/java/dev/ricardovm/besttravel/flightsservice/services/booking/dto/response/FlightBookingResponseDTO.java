package dev.ricardovm.besttravel.flightsservice.services.booking.dto.response;

public record FlightBookingResponseDTO(
        String bookingId,
        String flightId,
        String status) {
    public static class Builder {
        private String bookingId;
        private String flightId;
        private String status;

        public Builder bookingId(String bookingId) {
            this.bookingId = bookingId;
            return this;
        }

        public Builder flightId(String flightId) {
            this.flightId = flightId;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public FlightBookingResponseDTO build() {
            return new FlightBookingResponseDTO(bookingId, flightId, status);
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }
}
