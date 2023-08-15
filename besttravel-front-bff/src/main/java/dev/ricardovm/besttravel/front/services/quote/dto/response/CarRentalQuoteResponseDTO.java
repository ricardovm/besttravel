package dev.ricardovm.besttravel.front.services.quote.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CarRentalQuoteResponseDTO(
        String id,
        String rentalCompany,
        String model,
        String pickupLocation,
        String dropOffLocation,
        LocalDate pickupDate,
        LocalDate dropOffDate,
        BigDecimal price) {

    public static class Builder {
        private String id = UUID.randomUUID().toString();
        private String rentalCompany;
        private String model;
        private String pickupLocation;
        private String dropOffLocation;
        private LocalDate pickupDate;
        private LocalDate dropOffDate;
        private BigDecimal price;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder rentalCompany(String rentalCompany) {
            this.rentalCompany = rentalCompany;
            return this;
        }

        public Builder model(String model) {
            this.model = model;
            return this;
        }

        public Builder pickupLocation(String pickupLocation) {
            this.pickupLocation = pickupLocation;
            return this;
        }

        public Builder dropOffLocation(String dropOffLocation) {
            this.dropOffLocation = dropOffLocation;
            return this;
        }

        public Builder pickupDate(LocalDate pickupDate) {
            this.pickupDate = pickupDate;
            return this;
        }

        public Builder dropOffDate(LocalDate dropOffDate) {
            this.dropOffDate = dropOffDate;
            return this;
        }

        public Builder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public CarRentalQuoteResponseDTO build() {
            return new CarRentalQuoteResponseDTO(id, rentalCompany, model, pickupLocation, dropOffLocation, pickupDate, dropOffDate, price);
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }
}
