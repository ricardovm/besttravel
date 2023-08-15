package dev.ricardovm.besttravel.flightsservice.services.quote.dto.response;

import java.util.UUID;

public record QuoteResponseDTO(
        String id,
        String quoteId,
        FlightQuoteResponseDTO flight) {
    public static class Builder {
        private String id = UUID.randomUUID().toString();
        private String quoteId;
        private FlightQuoteResponseDTO flight;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder quoteId(String quoteId) {
            this.quoteId = quoteId;
            return this;
        }

        public Builder flight(FlightQuoteResponseDTO flight) {
            this.flight = flight;
            return this;
        }

        public QuoteResponseDTO build() {
            return new QuoteResponseDTO(id, quoteId, flight);
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }
}
