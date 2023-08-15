package dev.ricardovm.besttravel.accommodationsservice.services.booking;

import dev.ricardovm.besttravel.accommodationsservice.external.apis.AccommodationCompanyClient;
import dev.ricardovm.besttravel.accommodationsservice.services.booking.dto.request.BookingRequestDTO;
import dev.ricardovm.besttravel.accommodationsservice.services.booking.dto.response.AccommodationBookingResponseDTO;
import dev.ricardovm.besttravel.accommodationsservice.services.booking.dto.response.BookingResponseDTO;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class AccommodationBookingService {
    @Inject
    Event<BookingResponseDTO> bookingResponseEvent;

    @RestClient
    AccommodationCompanyClient accommodationCompanyClient;

    public void bookingRequest(@Observes BookingRequestDTO bookingRequest) {
        if (bookingRequest.accommodation() == null) {
            Log.debug(">> " + bookingRequest.quoteId() + " - No accommodation information");
            return;
        }

        Log.infov(">> {0}", bookingRequest);

        book(bookingRequest);
    }

    private void book(BookingRequestDTO bookingRequest) {
        var hotel = bookingRequest.accommodation().hotel();
        var hotelAPI = hotel.replaceAll(" ", "-").toLowerCase();
        accommodationCompanyClient.book(hotelAPI)
                .thenApply(status -> createResponse(bookingRequest, status))
                .thenAccept(response -> bookingResponseEvent.fireAsync(response));
    }

    private BookingResponseDTO createResponse(BookingRequestDTO request, String status) {
        return BookingResponseDTO.newBuilder()
                .bookingId(request.bookingId())
                .accommodation(AccommodationBookingResponseDTO.newBuilder()
                        .bookingId(request.bookingId())
                        .accommodationId(request.accommodation().id())
                        .status(status)
                        .build())
                .build();
    }
}
