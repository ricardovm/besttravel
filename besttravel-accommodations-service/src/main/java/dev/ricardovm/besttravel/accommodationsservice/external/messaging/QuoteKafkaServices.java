package dev.ricardovm.besttravel.accommodationsservice.external.messaging;

import dev.ricardovm.besttravel.accommodationsservice.services.quote.dto.request.QuoteRequestDTO;
import dev.ricardovm.besttravel.accommodationsservice.services.quote.dto.response.QuoteResponseDTO;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.enterprise.event.ObservesAsync;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
public class QuoteKafkaServices {
    @Inject
    @Channel("quote-responses")
    Emitter<QuoteResponseDTO> quoteResponsesEmitter;

    @Inject
    Event<QuoteRequestDTO> requestEvent;

    @Incoming("quote-requests")
    public void sendQuoteRequest(QuoteRequestDTO quoteRequest) {
        Log.infov(">> {0}", quoteRequest);
        requestEvent.fire(quoteRequest);
    }

    public void receiveQuoteResponse(@ObservesAsync QuoteResponseDTO quoteResponse) {
        Log.infov("<< {0}", quoteResponse);
        quoteResponsesEmitter.send(quoteResponse);
    }
}
