package dev.ricardovm.besttravel.flightsservice.services.quote.dto.request;

public record QuoteRequestDTO(
        String quoteId,
        FlightQuoteRequestDTO flight) {
}
