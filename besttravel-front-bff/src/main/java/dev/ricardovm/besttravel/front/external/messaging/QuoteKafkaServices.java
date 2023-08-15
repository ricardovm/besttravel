package dev.ricardovm.besttravel.front.external.messaging;

import dev.ricardovm.besttravel.front.services.quote.dto.request.QuoteRequestDTO;
import dev.ricardovm.besttravel.front.services.quote.dto.response.QuoteResponseDTO;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
public class QuoteKafkaServices {
    @Inject
    @Channel("quote-requests")
    Emitter<QuoteRequestDTO> emitter;

    @Inject
    Event<QuoteResponseDTO> responseEvent;

    public void sendQuoteRequest(@Observes QuoteRequestDTO quoteRequest) {
        Log.infov(">> {0}", quoteRequest);
        emitter.send(quoteRequest);
    }

    @Incoming("quote-responses")
    public void receiveQuoteResponse(QuoteResponseDTO quoteResponse) {
        Log.infov("<< {0}", quoteResponse);
        responseEvent.fire(quoteResponse);
    }
}
