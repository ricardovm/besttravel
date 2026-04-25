package dev.ricardovm.besttravel.orchestratorservice.quote.dto;

public record CarRentalQuoteRequest(
        String quoteId,
        CarRentalQuoteCommandDTO carRental) {
}
