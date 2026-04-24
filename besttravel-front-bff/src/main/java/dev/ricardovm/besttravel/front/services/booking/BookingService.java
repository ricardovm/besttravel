package dev.ricardovm.besttravel.front.services.booking;

import dev.ricardovm.besttravel.front.services.booking.dto.request.BookingRequestDTO;
import dev.ricardovm.besttravel.front.services.booking.dto.response.BookingResponseDTO;
import dev.ricardovm.besttravel.front.services.common.TimedCallbackRegistry;
import io.quarkus.logging.Log;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.time.Duration;
import java.util.function.Consumer;

@ApplicationScoped
public class BookingService {
    @Inject
    Event<BookingRequestDTO> requestEvent;

    @ConfigProperty(name = "besttravel.booking.callback.ttl-minutes", defaultValue = "3")
    int callbackTtlMinutes;

    private TimedCallbackRegistry<BookingResponseDTO> callbacks;

    @PostConstruct
    void init() {
        callbacks = new TimedCallbackRegistry<>(
                Duration.ofMinutes(callbackTtlMinutes),
                BookingResponseDTO::timeout);
    }

    @PreDestroy
    void destroy() {
        callbacks.shutdown();
    }

    public void sendBookingRequest(BookingRequestDTO bookingRequest, Consumer<BookingResponseDTO> callback) {
        Log.infov(">> {0}", bookingRequest);

        if (callback != null) {
            callbacks.register(bookingRequest.bookingId(), expectedResponses(bookingRequest), callback);
        }

        requestEvent.fire(bookingRequest);
    }

    public void receiveBookingResponse(@Observes BookingResponseDTO bookingResponse) {
        Log.infov("<< {0}", bookingResponse);

        if (callbacks.deliver(bookingResponse.bookingId(), bookingResponse)) {
            Log.info("++ Callback found");
        } else {
            Log.infov("++ Callback {0} not found", bookingResponse.bookingId());
        }
    }

    private int expectedResponses(BookingRequestDTO bookingRequest) {
        var expectedResponses = 0;

        if (bookingRequest.flight() != null) {
            expectedResponses++;
        }

        if (bookingRequest.accommodation() != null) {
            expectedResponses++;
        }

        if (bookingRequest.carRental() != null) {
            expectedResponses++;
        }

        return expectedResponses;
    }
}
