package dev.ricardovm.besttravel.carrentalservice.services.quote.dto.request;

public record QuoteRequestDTO(
        String quoteId,
        CarRentalQuoteRequestDTO carRental) {
}
