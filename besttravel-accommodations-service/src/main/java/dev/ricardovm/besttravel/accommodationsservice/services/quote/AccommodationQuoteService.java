package dev.ricardovm.besttravel.accommodationsservice.services.quote;

import dev.ricardovm.besttravel.accommodationsservice.external.apis.AccommodationCompanyClient;
import dev.ricardovm.besttravel.accommodationsservice.services.quote.dto.request.QuoteRequestDTO;
import dev.ricardovm.besttravel.accommodationsservice.services.quote.dto.response.AccommodationQuoteResponseDTO;
import dev.ricardovm.besttravel.accommodationsservice.services.quote.dto.response.QuoteResponseDTO;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.Random;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class AccommodationQuoteService {
    private static final String[] HOTELS = new String[]{"Hilton", "Plaza", "Inn", "Bourbon", "Ibis", "Sheraton"};

    @Inject
    Event<QuoteResponseDTO> quoteResponseEvent;

    @RestClient
    AccommodationCompanyClient accommodationCompanyClient;

    public void quoteRequest(@Observes QuoteRequestDTO quoteRequest) {
        if (quoteRequest.accommodation() == null) {
            Log.debug(">> " + quoteRequest.quoteId() + " - No accommodation information");
            return;
        }

        Log.infov(">> {0}", quoteRequest);

        quote(quoteRequest);
    }

    private void quote(QuoteRequestDTO quoteRequest) {
        final var accommodationCompanyResponses = new Random().nextInt(3);

        for (var i = 0; i <= accommodationCompanyResponses; i++) {
            final var quoteResponse = quoteWithAccommodationCompany(quoteRequest);
            quoteResponse.thenAccept(quoteResponseEvent::fireAsync);
        }
    }

    private CompletionStage<QuoteResponseDTO> quoteWithAccommodationCompany(QuoteRequestDTO quoteRequest) {
        var hotel = HOTELS[new Random().nextInt(HOTELS.length)];
        final var hotelAPI = hotel.replaceAll(" ", "-").toLowerCase();

        return accommodationCompanyClient.quote(hotelAPI)
                .thenApply(price -> {
                    var accommodationResponse = AccommodationQuoteResponseDTO.newBuilder()
                            .hotel(hotel)
                            .city(quoteRequest.accommodation().destination())
                            .checkInDate(quoteRequest.accommodation().checkInDate())
                            .checkOutDate(quoteRequest.accommodation().checkOutDate())
                            .price(price)
                            .build();

                    var quoteResponse = QuoteResponseDTO.newBuilder()
                            .quoteId(quoteRequest.quoteId())
                            .accommodation(accommodationResponse)
                            .build();

                    Log.infov("<< {0}", quoteResponse);

                    return quoteResponse;
                });
    }
}
