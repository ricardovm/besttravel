package dev.ricardovm.besttravel.accommodationsservice.external.apis;


import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.math.BigDecimal;
import java.util.concurrent.CompletionStage;

@Path("/")
@RegisterRestClient(configKey = "accommodation-company-api")
public interface AccommodationCompanyClient {

    @GET
    @Path("{company}/quote")
    CompletionStage<BigDecimal> quote(@PathParam("company") String company);

    @GET
    @Path("{company}/book")
    CompletionStage<String> book(@PathParam("company") String company);
}
