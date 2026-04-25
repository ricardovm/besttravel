package dev.ricardovm.besttravel.orchestratorservice.quote.dto;

public record AccommodationQuoteRequest(
        String quoteId,
        AccommodationQuoteCommandDTO accommodation) {
}
