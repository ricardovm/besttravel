package dev.ricardovm.besttravel.orchestratorservice.booking;

import dev.ricardovm.besttravel.orchestratorservice.booking.dto.AccommodationBookingRequest;
import dev.ricardovm.besttravel.orchestratorservice.booking.dto.BookingCommandDTO;
import dev.ricardovm.besttravel.orchestratorservice.booking.dto.CarRentalBookingRequest;
import dev.ricardovm.besttravel.orchestratorservice.booking.dto.FlightBookingRequest;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.enterprise.event.ObservesAsync;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class BookingKafkaServices {

    @Inject
    Event<BookingCommandDTO> commandEvent;

    @Inject
    @Channel("flight-booking-requests")
    Emitter<FlightBookingRequest> flightEmitter;

    @Inject
    @Channel("accommodation-booking-requests")
    Emitter<AccommodationBookingRequest> accommodationEmitter;

    @Inject
    @Channel("car-rental-booking-requests")
    Emitter<CarRentalBookingRequest> carRentalEmitter;

    @Incoming("booking-commands")
    public CompletionStage<Void> receiveCommand(BookingCommandDTO command) {
        Log.infov(">> {0}", command);
        return commandEvent
                .fireAsync(command)
                .thenApply(e -> null);
    }

    public void sendFlightBookingRequest(@ObservesAsync FlightBookingRequest request) {
        Log.infov("<< {0}", request);
        flightEmitter.send(request);
    }

    public void sendAccommodationBookingRequest(@ObservesAsync AccommodationBookingRequest request) {
        Log.infov("<< {0}", request);
        accommodationEmitter.send(request);
    }

    public void sendCarRentalBookingRequest(@ObservesAsync CarRentalBookingRequest request) {
        Log.infov("<< {0}", request);
        carRentalEmitter.send(request);
    }
}
