package dev.ricardovm.besttravel.carrentalservice.external.apis;


import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import io.smallrye.faulttolerance.api.ExponentialBackoff;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.math.BigDecimal;
import java.util.concurrent.CompletionStage;

@Path("/")
@RegisterRestClient(configKey = "car-rental-company-api")
public interface CarRentalCompanyClient {

    @GET
    @Path("{company}/quote")
    @Retry(maxRetries = 3, delay = 500, jitter = 100)
    @ExponentialBackoff(factor = 2, maxDelay = 10000)
    CompletionStage<BigDecimal> quote(@PathParam("company") String company);

    @GET
    @Path("{company}/book")
    @Retry(maxRetries = 3, delay = 500, jitter = 100)
    @ExponentialBackoff(factor = 2, maxDelay = 10000)
    CompletionStage<String> book(@PathParam("company") String company);
}
