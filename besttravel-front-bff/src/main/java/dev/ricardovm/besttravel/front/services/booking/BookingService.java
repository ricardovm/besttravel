package dev.ricardovm.besttravel.front.services.booking;

import dev.ricardovm.besttravel.front.services.booking.dto.request.BookingRequestDTO;
import dev.ricardovm.besttravel.front.services.booking.dto.response.BookingResponseDTO;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Consumer;

@ApplicationScoped
public class BookingService {
    @Inject
    Event<BookingRequestDTO> requestEvent;

    private ConcurrentMap<String, Consumer<BookingResponseDTO>> callbacks = new ConcurrentHashMap<>();

    public void sendBookingRequest(BookingRequestDTO bookingRequest, Consumer<BookingResponseDTO> callback) {
        Log.infov(">> {0}", bookingRequest);

        if (callback != null) {
            callbacks.put(bookingRequest.bookingId(), callback);
        }

        requestEvent.fire(bookingRequest);
    }

    public void receiveBookingResponse(@Observes BookingResponseDTO bookingResponse) {
        Log.infov("<< {0}", bookingResponse);

        var callback = callbacks.get(bookingResponse.bookingId());

        if (callback != null) {
            Log.info("++ Callback found");
            callback.accept(bookingResponse);
        } else {
            Log.infov("++ Callback {0} not found", bookingResponse.bookingId());
        }
    }
}
