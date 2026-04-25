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
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class QuoteKafkaServices {

    @Inject
    Event<QuoteCommandDTO> commandEvent;

    @Inject
    @Channel("flight-quote-requests")
    Emitter<FlightQuoteRequest> flightEmitter;

    @Inject
    @Channel("accommodation-quote-requests")
    Emitter<AccommodationQuoteRequest> accommodationEmitter;

    @Inject
    @Channel("car-rental-quote-requests")
    Emitter<CarRentalQuoteRequest> carRentalEmitter;

    @Incoming("quote-commands")
    public CompletionStage<Void> receiveCommand(QuoteCommandDTO cmd) {
        Log.infov(">> {0}", cmd);
        return commandEvent.fireAsync(cmd).thenApply(e -> null);
    }

    public void sendFlightQuoteRequest(@ObservesAsync FlightQuoteRequest req) {
        Log.infov("<< {0}", req);
        flightEmitter.send(req);
    }

    public void sendAccommodationQuoteRequest(@ObservesAsync AccommodationQuoteRequest req) {
        Log.infov("<< {0}", req);
        accommodationEmitter.send(req);
    }

    public void sendCarRentalQuoteRequest(@ObservesAsync CarRentalQuoteRequest req) {
        Log.infov("<< {0}", req);
        carRentalEmitter.send(req);
    }
}
