package dev.ricardovm.besttravel.carrentalservice.services.quote;

import dev.ricardovm.besttravel.carrentalservice.external.apis.CarRentalCompanyClient;
import dev.ricardovm.besttravel.carrentalservice.services.quote.dto.request.QuoteRequestDTO;
import dev.ricardovm.besttravel.carrentalservice.services.quote.dto.response.CarRentalQuoteResponseDTO;
import dev.ricardovm.besttravel.carrentalservice.services.quote.dto.response.QuoteResponseDTO;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.Random;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class CarRentalQuoteService {
    private static final String[] CAR_COMPANIES = new String[]{"Localiza", "Movida", "Unidas", "Hertz", "Avis", "Budget"};

    private static final String[] CAR_MODELS = new String[]{"Toyota Corola", "Honda Civic", "Volkswagen Golf", "Ford Focus", "Chevrolet Cruze", "Hyundai Elantra"};

    @Inject
    Event<QuoteResponseDTO> quoteResponseEvent;

    @RestClient
    CarRentalCompanyClient carRentalCompanyClient;

    public void quoteRequest(@Observes QuoteRequestDTO quoteRequest) {
        if (quoteRequest.carRental() == null) {
            Log.debug(">> " + quoteRequest.quoteId() + " - No car rental information");
            return;
        }

        Log.infov(">> {0}", quoteRequest);

        quote(quoteRequest);
    }

    private void quote(QuoteRequestDTO quoteRequest) {
        final var carRentalCompanyResponses = new Random().nextInt(3);

        for (var i = 0; i <= carRentalCompanyResponses; i++) {
            final var quoteResponse = quoteWithCarRentalCompany(quoteRequest);
            quoteResponse.thenAccept(quoteResponseEvent::fireAsync);
        }
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
