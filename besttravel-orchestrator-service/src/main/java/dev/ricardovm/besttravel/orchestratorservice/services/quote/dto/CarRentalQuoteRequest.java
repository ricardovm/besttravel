package dev.ricardovm.besttravel.orchestratorservice.services.quote.dto;

public record CarRentalQuoteRequest(
        String quoteId,
        CarRentalQuoteCommandDTO carRental) {
}
