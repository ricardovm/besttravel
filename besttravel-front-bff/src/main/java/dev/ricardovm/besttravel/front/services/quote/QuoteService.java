package dev.ricardovm.besttravel.front.services.quote;

import dev.ricardovm.besttravel.front.services.quote.dto.request.QuoteRequestDTO;
import dev.ricardovm.besttravel.front.services.quote.dto.response.QuoteResponseDTO;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Consumer;

@ApplicationScoped
public class QuoteService {
    @Inject
    Event<QuoteRequestDTO> requestEvent;

    private ConcurrentMap<String, Consumer<QuoteResponseDTO>> callbacks = new ConcurrentHashMap<>();

    public void sendQuoteRequest(QuoteRequestDTO quoteRequest, Consumer<QuoteResponseDTO> callback) {
        Log.infov(">> {0}", quoteRequest);

        if (callback != null) {
            callbacks.put(quoteRequest.quoteId(), callback);
        }

        requestEvent.fire(quoteRequest);
    }

    public void receiveQuoteResponse(@Observes QuoteResponseDTO quoteResponse) {
        Log.infov("<< {0}", quoteResponse);

        var callback = callbacks.get(quoteResponse.quoteId());

        if (callback != null) {
            Log.info("++ Callback found");
            callback.accept(quoteResponse);
        } else {
            Log.infov("++ Callback {0} not found", quoteResponse.quoteId());
        }
    }
}
