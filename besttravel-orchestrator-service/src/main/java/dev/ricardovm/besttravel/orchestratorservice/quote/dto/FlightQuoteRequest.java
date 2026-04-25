package dev.ricardovm.besttravel.orchestratorservice.quote.dto;

public record FlightQuoteRequest(
        String quoteId,
        FlightQuoteCommandDTO flight) {
}
