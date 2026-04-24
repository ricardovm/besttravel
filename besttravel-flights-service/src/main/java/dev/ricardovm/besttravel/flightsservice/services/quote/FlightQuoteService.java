package dev.ricardovm.besttravel.flightsservice.services.quote;

import dev.ricardovm.besttravel.flightsservice.external.apis.FlightCompanyClient;
import dev.ricardovm.besttravel.flightsservice.services.quote.dto.request.QuoteRequestDTO;
import dev.ricardovm.besttravel.flightsservice.services.quote.dto.response.FlightQuoteResponseDTO;
import dev.ricardovm.besttravel.flightsservice.services.quote.dto.response.QuoteResponseDTO;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.enterprise.event.ObservesAsync;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class FlightQuoteService {
    private static final String[] FLIGHT_COMPANIES = new String[]{
            "Gol", "Latam", "Azul", "KLM", "American Airlines", "Air France", "British Airways", "TAP", "Emirates",
            "Lufthansa", "Turkish Airlines", "United Airlines", "Delta Airlines", "Air Canada"};

    @Inject
    Event<QuoteResponseDTO> quoteResponseEvent;

    @RestClient
    FlightCompanyClient flightCompanyClient;

    public void quoteRequest(@ObservesAsync QuoteRequestDTO quoteRequest) {
        if (quoteRequest.flight() == null) {
            Log.debug(">> " + quoteRequest.quoteId() + " - No flight information");
            return;
        }

        Log.infov(">> {0}", quoteRequest);

        quote(quoteRequest);
    }

    private void quote(QuoteRequestDTO quoteRequest) {
        final var count = new Random().nextInt(3) + 1;
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        for (var i = 0; i < count; i++) {
            futures.add(quoteWithFlightcompany(quoteRequest)
                    .thenAccept(quoteResponseEvent::fireAsync)
                    .exceptionally(e -> {
                        Log.errorf(e, "Failed to get flight quote for quoteId=%s", quoteRequest.quoteId());
                        return null;
                    })
                    .toCompletableFuture());
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    }

    private CompletionStage<QuoteResponseDTO> quoteWithFlightcompany(QuoteRequestDTO quoteRequest) {
        var flightCompany = FLIGHT_COMPANIES[new Random().nextInt(FLIGHT_COMPANIES.length)];
        final var flightCompanyAPI = flightCompany.replaceAll(" ", "-").toLowerCase();


        return flightCompanyClient.quote(flightCompanyAPI)
                .thenApply(price -> {
                    var flightQuoteResponse = FlightQuoteResponseDTO.newBuilder()
                            .flightCompany(flightCompany)
                            .origin(quoteRequest.flight().origin())
                            .destination(quoteRequest.flight().destination())
                            .departureDate(quoteRequest.flight().departureDate())
                            .returnDate(quoteRequest.flight().returnDate())
                            .price(price)
                            .build();

                    var quoteResponse = QuoteResponseDTO.newBuilder()
                            .quoteId(quoteRequest.quoteId())
                            .flight(flightQuoteResponse)
                            .build();

                    Log.infov("<< {0}", quoteResponse);

                    return quoteResponse;
                });
    }
}
