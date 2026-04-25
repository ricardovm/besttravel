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

    public void route(@ObservesAsync QuoteCommandDTO cmd) {
        Log.infov(">> routing quoteId={0}", cmd.quoteId());
        if (cmd.flight() != null) {
            flightEvent.fireAsync(new FlightQuoteRequest(cmd.quoteId(), cmd.flight()));
        }
        if (cmd.accommodation() != null) {
            accommodationEvent.fireAsync(new AccommodationQuoteRequest(cmd.quoteId(), cmd.accommodation()));
        }
        if (cmd.carRental() != null) {
            carRentalEvent.fireAsync(new CarRentalQuoteRequest(cmd.quoteId(), cmd.carRental()));
        }
    }
}
