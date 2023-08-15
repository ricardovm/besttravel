package dev.ricardovm.besttravel.front.services.booking.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AccommodationBookingRequestDTO(
        String id,
        String hotel,
        String city,
        LocalDate checkInDate,
        LocalDate checkOutDate,
        BigDecimal price) {

    public static class Builder {
        private String id;
        private String hotel;
        private String city;
        private LocalDate checkInDate;
        private LocalDate checkOutDate;
        private BigDecimal price;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder hotel(String hotel) {
            this.hotel = hotel;
            return this;
        }

        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public Builder checkInDate(LocalDate checkInDate) {
            this.checkInDate = checkInDate;
            return this;
        }

        public Builder checkOutDate(LocalDate checkOutDate) {
            this.checkOutDate = checkOutDate;
            return this;
        }

        public Builder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public AccommodationBookingRequestDTO build() {
            return new AccommodationBookingRequestDTO(id, hotel, city, checkInDate, checkOutDate, price);
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }
}
