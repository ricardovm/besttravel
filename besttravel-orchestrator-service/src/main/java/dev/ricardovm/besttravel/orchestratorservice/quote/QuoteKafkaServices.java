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
    public CompletionStage<Void> receiveCommand(QuoteCommandDTO command) {
        Log.infov(">> {0}", command);
        return commandEvent
                .fireAsync(command)
                .thenApply(e -> null);
    }

    public void sendFlightQuoteRequest(@ObservesAsync FlightQuoteRequest request) {
        Log.infov("<< {0}", request);
        flightEmitter.send(request);
    }

    public void sendAccommodationQuoteRequest(@ObservesAsync AccommodationQuoteRequest request) {
        Log.infov("<< {0}", request);
        accommodationEmitter.send(request);
    }

    public void sendCarRentalQuoteRequest(@ObservesAsync CarRentalQuoteRequest request) {
        Log.infov("<< {0}", request);
        carRentalEmitter.send(request);
    }
}
