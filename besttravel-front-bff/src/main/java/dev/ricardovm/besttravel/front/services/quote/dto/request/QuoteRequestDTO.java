package dev.ricardovm.besttravel.front.services.quote.dto.request;

public record QuoteRequestDTO(
        String quoteId,
        FlightQuoteRequestDTO flight,
        AccommodationQuoteRequestDTO accommodation,
        CarRentalQuoteRequestDTO carRental) {

    public static class Builder {
        private String quoteId;
        private FlightQuoteRequestDTO flight;
        private AccommodationQuoteRequestDTO accommodation;
        private CarRentalQuoteRequestDTO carRental;

        Builder() {
        }

        public Builder quoteId(String quoteId) {
            this.quoteId = quoteId;
            return this;
        }

        public Builder flight(FlightQuoteRequestDTO flight) {
            this.flight = flight;
            return this;
        }

        public Builder accommodation(AccommodationQuoteRequestDTO accommodation) {
            this.accommodation = accommodation;
            return this;
        }

        public Builder carRental(CarRentalQuoteRequestDTO carRental) {
            this.carRental = carRental;
            return this;
        }

        public QuoteRequestDTO build() {
            return new QuoteRequestDTO(quoteId, flight, accommodation, carRental);
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }
}
