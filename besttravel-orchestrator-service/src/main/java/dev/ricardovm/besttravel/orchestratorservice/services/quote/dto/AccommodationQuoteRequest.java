package dev.ricardovm.besttravel.orchestratorservice.services.quote.dto;

public record AccommodationQuoteRequest(
        String quoteId,
        AccommodationQuoteCommandDTO accommodation) {
}
