package dev.ricardovm.besttravel.accommodationsservice.services.booking.dto.response;

public record BookingResponseDTO(
        String bookingId,
        AccommodationBookingResponseDTO accommodation) {
    public static class Builder {
        private String bookingId;
        private AccommodationBookingResponseDTO accommodation;

        public Builder bookingId(String bookingId) {
            this.bookingId = bookingId;
            return this;
        }

        public Builder accommodation(AccommodationBookingResponseDTO accommodation) {
            this.accommodation = accommodation;
            return this;
        }

        public BookingResponseDTO build() {
            return new BookingResponseDTO(bookingId, accommodation);
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }
}
