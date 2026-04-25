package dev.ricardovm.besttravel.flightsservice.services.booking;

import dev.ricardovm.besttravel.flightsservice.external.apis.FlightCompanyClient;
import dev.ricardovm.besttravel.flightsservice.services.booking.dto.request.BookingRequestDTO;
import dev.ricardovm.besttravel.flightsservice.services.booking.dto.response.BookingResponseDTO;
import dev.ricardovm.besttravel.flightsservice.services.booking.dto.response.FlightBookingResponseDTO;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.enterprise.event.ObservesAsync;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class FlightBookingService {
    @Inject
    Event<BookingResponseDTO> bookingResponseEvent;

    @RestClient
    FlightCompanyClient flightCompanyClient;

    public void bookingRequest(@ObservesAsync BookingRequestDTO bookingRequest) {
        Log.infov(">> {0}", bookingRequest);

        book(bookingRequest);
    }

    private void book(BookingRequestDTO bookingRequest) {
        var flightCompany = bookingRequest.flight().flightCompany();
        var flightCompanyAPI = flightCompany.replaceAll(" ", "-").toLowerCase();
        flightCompanyClient.book(flightCompanyAPI)
                .thenApply(status -> createResponse(bookingRequest, status))
                .thenAccept(response -> bookingResponseEvent.fireAsync(response))
                .toCompletableFuture()
                .join();
    }

    private BookingResponseDTO createResponse(BookingRequestDTO request, String status) {
        return BookingResponseDTO.newBuilder()
                .bookingId(request.bookingId())
                .flight(FlightBookingResponseDTO.newBuilder()
                        .bookingId(request.bookingId())
                        .flightId(request.flight().id())
                        .status(status)
                        .build())
                .build();
    }
}
