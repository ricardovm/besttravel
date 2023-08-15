package dev.ricardovm.besttravel.flightsservice.services.quote.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record FlightQuoteResponseDTO(
        String id,
        String flighCompany,
        String origin,
        String destination,
        LocalDate departureDate,
        LocalDate returnDate,
        BigDecimal price) {
    public static class Builder {
        private String id = UUID.randomUUID().toString();
        private String flighCompany;
        private String origin;
        private String destination;
        private LocalDate departureDate;
        private LocalDate returnDate;
        private BigDecimal price;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder flighCompany(String flighCompany) {
            this.flighCompany = flighCompany;
            return this;
        }

        public Builder origin(String origin) {
            this.origin = origin;
            return this;
        }

        public Builder destination(String destination) {
            this.destination = destination;
            return this;
        }

        public Builder departureDate(LocalDate departureDate) {
            this.departureDate = departureDate;
            return this;
        }

        public Builder returnDate(LocalDate returnDate) {
            this.returnDate = returnDate;
            return this;
        }

        public Builder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public FlightQuoteResponseDTO build() {
            return new FlightQuoteResponseDTO(id, flighCompany, origin, destination, departureDate, returnDate, price);
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }
}
