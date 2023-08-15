package dev.ricardovm.besttravel.bookingservice.external.messaging;

import dev.ricardovm.besttravel.bookingservice.services.dto.request.BookingRequestDTO;
import dev.ricardovm.besttravel.bookingservice.services.dto.response.BookingResponseDTO;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
public class BookingKafkaServices {
    @Inject
    Event<BookingRequestDTO> requestEvent;

    @Inject
    Event<BookingResponseDTO> responseEvent;

    @Incoming("booking-requests")
    public void receiveBookingRequest(BookingRequestDTO bookingRequest) {
        Log.infov("<< {0}", bookingRequest);
        requestEvent.fire(bookingRequest);
    }

    @Incoming("booking-responses")
    public void receiveBookingResponse(BookingResponseDTO bookingResponse) {
        Log.infov("<< {0}", bookingResponse);
        responseEvent.fire(bookingResponse);
    }
}
