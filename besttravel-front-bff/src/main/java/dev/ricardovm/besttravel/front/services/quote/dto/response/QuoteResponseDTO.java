package dev.ricardovm.besttravel.front.services.quote.dto.response;

public record QuoteResponseDTO(
        String id,
        String quoteId,
        FlightQuoteResponseDTO flight,
        AccommodationQuoteResponseDTO accommodation,
        CarRentalQuoteResponseDTO carRental) {
}
