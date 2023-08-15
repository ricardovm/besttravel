package dev.ricardovm.besttravel.front.external.messaging;

import dev.ricardovm.besttravel.front.services.booking.dto.request.BookingRequestDTO;
import dev.ricardovm.besttravel.front.services.booking.dto.response.BookingResponseDTO;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
public class BookingKafkaServices {
    @Inject
    @Channel("booking-requests")
    Emitter<BookingRequestDTO> emitter;

    @Inject
    Event<BookingResponseDTO> responseEvent;

    public void sendBookingRequest(@Observes BookingRequestDTO bookingRequest) {
        Log.infov(">> {0}", bookingRequest);
        emitter.send(bookingRequest);
    }

    @Incoming("booking-responses")
    public void receiveNookingResponse(BookingResponseDTO bookingResponse) {
        Log.infov("<< {0}", bookingResponse);
        responseEvent.fire(bookingResponse);
    }
}
