package dev.ricardovm.besttravel.externalcompany;

import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

@Path("/")
public class CompanyResource {
    @GET
    @Path("{company}/quote")
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<BigDecimal> quote(@PathParam("company") String company) {
        Log.infov("Quote requested for company {0}", company);

        return Uni.createFrom().item(
                BigDecimal.valueOf(new Random().nextDouble() * 100)
                        .setScale(2, RoundingMode.HALF_EVEN));
    }

    @GET
    @Path("{company}/book")
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<String> book(@PathParam("company") String company) {
        Log.infov("Book requested for company {0}", company);
        return Uni.createFrom().item("CONFIRMED");
    }
}
