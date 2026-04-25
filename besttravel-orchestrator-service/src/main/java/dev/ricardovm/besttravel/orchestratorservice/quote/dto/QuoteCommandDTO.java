package dev.ricardovm.besttravel.orchestratorservice.quote.dto;

public record QuoteCommandDTO(
        String quoteId,
        FlightQuoteCommandDTO flight,
        AccommodationQuoteCommandDTO accommodation,
        CarRentalQuoteCommandDTO carRental) {
}
