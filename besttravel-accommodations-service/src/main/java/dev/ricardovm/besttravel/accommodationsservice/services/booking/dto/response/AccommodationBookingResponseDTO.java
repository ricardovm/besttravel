package dev.ricardovm.besttravel.accommodationsservice.services.booking.dto.response;

public record AccommodationBookingResponseDTO(
        String bookingId,
        String accommodationId,
        String status) {
    public static class Builder {
        private String bookingId;
        private String accommodationId;
        private String status;

        public Builder bookingId(String bookingId) {
            this.bookingId = bookingId;
            return this;
        }

        public Builder accommodationId(String accommodationId) {
            this.accommodationId = accommodationId;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public AccommodationBookingResponseDTO build() {
            return new AccommodationBookingResponseDTO(bookingId, accommodationId, status);
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }
}
