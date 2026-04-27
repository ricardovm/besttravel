package dev.ricardovm.besttravel.orchestratorservice.services.quote.dto;

public record QuoteCommandDTO(
        String quoteId,
        FlightQuoteCommandDTO flight,
        AccommodationQuoteCommandDTO accommodation,
        CarRentalQuoteCommandDTO carRental) {
}
