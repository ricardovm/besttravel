package dev.ricardovm.besttravel.front.services.quote.dto.response;

public record QuoteResponseDTO(
        String id,
        String quoteId,
        FlightQuoteResponseDTO flight,
        AccommodationQuoteResponseDTO accommodation,
        CarRentalQuoteResponseDTO carRental,
        Boolean timedOut) {
    public static QuoteResponseDTO timeout(String quoteId) {
        return new QuoteResponseDTO(null, quoteId, null, null, null, true);
    }
}
