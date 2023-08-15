package dev.ricardovm.besttravel.accommodationsservice.services.quote.dto.response;

import java.util.UUID;

public record QuoteResponseDTO(
        String id,
        String quoteId,
        AccommodationQuoteResponseDTO accommodation) {
    public static class Builder {
        private String id = UUID.randomUUID().toString();
        private String quoteId;
        private AccommodationQuoteResponseDTO accommodation;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder quoteId(String quoteId) {
            this.quoteId = quoteId;
            return this;
        }

        public Builder accommodation(AccommodationQuoteResponseDTO accommodation) {
            this.accommodation = accommodation;
            return this;
        }

        public QuoteResponseDTO build() {
            return new QuoteResponseDTO(id, quoteId, accommodation);
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }
}
