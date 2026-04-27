package dev.ricardovm.besttravel.orchestratorservice.services.quote.dto;

public record FlightQuoteRequest(
        String quoteId,
        FlightQuoteCommandDTO flight) {
}
