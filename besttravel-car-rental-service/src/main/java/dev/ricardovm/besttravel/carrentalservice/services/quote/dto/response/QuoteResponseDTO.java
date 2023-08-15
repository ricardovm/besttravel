package dev.ricardovm.besttravel.carrentalservice.services.quote.dto.response;

import java.util.UUID;

public record QuoteResponseDTO(
        String id,
        String quoteId,
        CarRentalQuoteResponseDTO carRental) {
    public static class Builder {
        private String id = UUID.randomUUID().toString();
        private String quoteId;
        private CarRentalQuoteResponseDTO carRental;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder quoteId(String quoteId) {
            this.quoteId = quoteId;
            return this;
        }

        public Builder carRental(CarRentalQuoteResponseDTO carRental) {
            this.carRental = carRental;
            return this;
        }

        public QuoteResponseDTO build() {
            return new QuoteResponseDTO(id, quoteId, carRental);
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }
}