package dev.ricardovm.besttravel.front.services.quote;

import dev.ricardovm.besttravel.front.services.common.TimedCallbackRegistry;
import dev.ricardovm.besttravel.front.services.quote.dto.request.QuoteRequestDTO;
import dev.ricardovm.besttravel.front.services.quote.dto.response.QuoteResponseDTO;
import io.quarkus.logging.Log;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.time.Duration;
import java.util.function.Consumer;

@ApplicationScoped
public class QuoteService {
    @Inject
    Event<QuoteRequestDTO> requestEvent;

    @ConfigProperty(name = "besttravel.quote.callback.ttl-seconds", defaultValue = "30")
    int callbackTtlSeconds;

    private TimedCallbackRegistry<QuoteResponseDTO> callbacks;

    @PostConstruct
    void init() {
        callbacks = new TimedCallbackRegistry<>(
                Duration.ofSeconds(callbackTtlSeconds),
                QuoteResponseDTO::timeout);
    }

    @PreDestroy
    void destroy() {
        callbacks.shutdown();
    }

    public void sendQuoteRequest(QuoteRequestDTO quoteRequest, Consumer<QuoteResponseDTO> callback) {
        Log.infov(">> {0}", quoteRequest);

        if (callback != null) {
            callbacks.register(quoteRequest.quoteId(), callback);
        }

        requestEvent.fire(quoteRequest);
    }

    public void receiveQuoteResponse(@Observes QuoteResponseDTO quoteResponse) {
        Log.infov("<< {0}", quoteResponse);

        if (callbacks.deliver(quoteResponse.quoteId(), quoteResponse)) {
            Log.info("++ Callback found");
        } else {
            Log.infov("++ Callback {0} not found", quoteResponse.quoteId());
        }
    }
}
