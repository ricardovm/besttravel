package dev.ricardovm.besttravel.flightsservice.external.apis;


import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.math.BigDecimal;
import java.util.concurrent.CompletionStage;

@Path("/")
@RegisterRestClient(configKey = "flight-company-api")
public interface FlightCompanyClient {

    @GET
    @Path("{company}/quote")
    CompletionStage<BigDecimal> quote(@PathParam("company") String company);

    @GET
    @Path("{company}/book")
    CompletionStage<String> book(@PathParam("company") String company);
}
