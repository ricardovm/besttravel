package dev.ricardovm.besttravel.accommodationsservice.services.quote.dto.request;

public record QuoteRequestDTO(
        String quoteId,
        AccommodationQuoteRequestDTO accommodation) {
}
