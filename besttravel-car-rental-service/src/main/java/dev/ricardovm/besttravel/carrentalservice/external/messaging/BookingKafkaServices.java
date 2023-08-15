package dev.ricardovm.besttravel.carrentalservice.external.messaging;

import dev.ricardovm.besttravel.carrentalservice.services.booking.dto.request.BookingRequestDTO;
import dev.ricardovm.besttravel.carrentalservice.services.booking.dto.response.BookingResponseDTO;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.enterprise.event.ObservesAsync;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
public class BookingKafkaServices {
    @Inject
    @Channel("booking-responses")
    Emitter<BookingResponseDTO> bookingResponsesEmitter;

    @Inject
    Event<BookingRequestDTO> requestEvent;

    @Incoming("booking-requests")
    public void sendBookingRequest(BookingRequestDTO bookingRequest) {
        Log.infov(">> {0}", bookingRequest);
        requestEvent.fire(bookingRequest);
    }

    public void receiveBookingResponse(@ObservesAsync BookingResponseDTO bookingResponse) {
        Log.infov("<< {0}", bookingResponse);
        bookingResponsesEmitter.send(bookingResponse);
    }
}
