package dev.ricardovm.besttravel.carrentalservice.services.booking;

import dev.ricardovm.besttravel.carrentalservice.external.apis.CarRentalCompanyClient;
import dev.ricardovm.besttravel.carrentalservice.services.booking.dto.request.BookingRequestDTO;
import dev.ricardovm.besttravel.carrentalservice.services.booking.dto.response.BookingResponseDTO;
import dev.ricardovm.besttravel.carrentalservice.services.booking.dto.response.CarRentalBookingResponseDTO;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class CarRentalBookingService {
    @Inject
    Event<BookingResponseDTO> bookingResponseEvent;

    @RestClient
    CarRentalCompanyClient carRentalCompanyClient;

    public void bookingRequest(@Observes BookingRequestDTO bookingRequest) {
        if (bookingRequest.carRental() == null) {
            Log.debug(">> " + bookingRequest.quoteId() + " - No car rental information");
            return;
        }

        Log.infov(">> {0}", bookingRequest);

        book(bookingRequest);
    }

    private void book(BookingRequestDTO bookingRequest) {
        var carCompany = bookingRequest.carRental().rentalCompany();
        var carCompanyAPI = carCompany.replaceAll(" ", "-").toLowerCase();
        carRentalCompanyClient.book(carCompanyAPI)
                .thenApply(status -> createResponse(bookingRequest, status))
                .thenAccept(response -> bookingResponseEvent.fireAsync(response));
    }

    private BookingResponseDTO createResponse(BookingRequestDTO request, String status) {
        return BookingResponseDTO.newBuilder()
                .bookingId(request.bookingId())
                .carRental(CarRentalBookingResponseDTO.newBuilder()
                        .bookingId(request.bookingId())
                        .carRentalId(request.carRental().id())
                        .status(status)
                        .build())
                .build();
    }
}
