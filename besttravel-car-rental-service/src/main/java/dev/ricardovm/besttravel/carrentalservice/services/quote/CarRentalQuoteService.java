package dev.ricardovm.besttravel.carrentalservice.services.quote;

import dev.ricardovm.besttravel.carrentalservice.external.apis.CarRentalCompanyClient;
import dev.ricardovm.besttravel.carrentalservice.services.quote.dto.request.QuoteRequestDTO;
import dev.ricardovm.besttravel.carrentalservice.services.quote.dto.response.CarRentalQuoteResponseDTO;
import dev.ricardovm.besttravel.carrentalservice.services.quote.dto.response.QuoteResponseDTO;
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
public class CarRentalQuoteService {
    private static final String[] CAR_COMPANIES = new String[]{"Localiza", "Movida", "Unidas", "Hertz", "Avis", "Budget"};

    private static final String[] CAR_MODELS = new String[]{"Toyota Corola", "Honda Civic", "Volkswagen Golf", "Ford Focus", "Chevrolet Cruze", "Hyundai Elantra"};

    @Inject
    Event<QuoteResponseDTO> quoteResponseEvent;

    @RestClient
    CarRentalCompanyClient carRentalCompanyClient;

    public void quoteRequest(@ObservesAsync QuoteRequestDTO quoteRequest) {
        Log.infov(">> {0}", quoteRequest);

        quote(quoteRequest);
    }

    private void quote(QuoteRequestDTO quoteRequest) {
        final var count = new Random().nextInt(3) + 1;
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        for (var i = 0; i < count; i++) {
            futures.add(quoteWithCarRentalCompany(quoteRequest)
                    .thenAccept(quoteResponseEvent::fireAsync)
                    .exceptionally(e -> {
                        Log.errorf(e, "Failed to get car rental quote for quoteId=%s", quoteRequest.quoteId());
                        return null;
                    })
                    .toCompletableFuture());
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    }

    private CompletionStage<QuoteResponseDTO> quoteWithCarRentalCompany(QuoteRequestDTO quoteRequest) {
        var carCompany = CAR_COMPANIES[new Random().nextInt(CAR_COMPANIES.length)];
        var carModel = CAR_MODELS[new Random().nextInt(CAR_MODELS.length)];
        final var carCompanyAPI = carCompany.replaceAll(" ", "-").toLowerCase();

        return carRentalCompanyClient.quote(carCompanyAPI)
                .thenApply(price -> {
                    var carRentalResponse = CarRentalQuoteResponseDTO.newBuilder()
                            .rentalCompany(carCompany)
                            .model(carModel)
                            .pickupLocation(quoteRequest.carRental().pickupLocation())
                            .dropOffLocation(quoteRequest.carRental().dropOffLocation())
                            .pickupDate(quoteRequest.carRental().pickupDate())
                            .dropOffDate(quoteRequest.carRental().dropOffDate())
                            .price(price)
                            .build();

                    var quoteResponse = QuoteResponseDTO.newBuilder()
                            .quoteId(quoteRequest.quoteId())
                            .carRental(carRentalResponse)
                            .build();

                    Log.infov("<< {0}", quoteResponse);

                    return quoteResponse;
                });
    }
}
