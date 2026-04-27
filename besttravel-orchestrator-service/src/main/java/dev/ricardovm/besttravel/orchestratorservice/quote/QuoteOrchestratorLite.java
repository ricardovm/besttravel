package dev.ricardovm.besttravel.orchestratorservice.quote;

import dev.ricardovm.besttravel.orchestratorservice.quote.dto.AccommodationQuoteRequest;
import dev.ricardovm.besttravel.orchestratorservice.quote.dto.CarRentalQuoteRequest;
import dev.ricardovm.besttravel.orchestratorservice.quote.dto.FlightQuoteRequest;
import dev.ricardovm.besttravel.orchestratorservice.quote.dto.QuoteCommandDTO;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.enterprise.event.ObservesAsync;
import jakarta.inject.Inject;

@ApplicationScoped
public class QuoteOrchestratorLite {

    @Inject
    Event<FlightQuoteRequest> flightEvent;

    @Inject
    Event<AccommodationQuoteRequest> accommodationEvent;

    @Inject
    Event<CarRentalQuoteRequest> carRentalEvent;

    public void route(@ObservesAsync QuoteCommandDTO quoteCommand) {
        Log.infov(">> routing quoteId={0}", quoteCommand.quoteId());

        if (quoteCommand.flight() != null) {
            flightEvent.fireAsync(new FlightQuoteRequest(quoteCommand.quoteId(), quoteCommand.flight()));
        }

        if (quoteCommand.accommodation() != null) {
            accommodationEvent.fireAsync(new AccommodationQuoteRequest(quoteCommand.quoteId(), quoteCommand.accommodation()));
        }

        if (quoteCommand.carRental() != null) {
            carRentalEvent.fireAsync(new CarRentalQuoteRequest(quoteCommand.quoteId(), quoteCommand.carRental()));
        }
    }
}
