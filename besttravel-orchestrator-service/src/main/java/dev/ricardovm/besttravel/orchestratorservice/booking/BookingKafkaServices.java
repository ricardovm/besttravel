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
    public CompletionStage<Void> receiveCommand(BookingCommandDTO cmd) {
        Log.infov(">> {0}", cmd);
        return commandEvent.fireAsync(cmd).thenApply(e -> null);
    }

    public void sendFlightBookingRequest(@ObservesAsync FlightBookingRequest req) {
        Log.infov("<< {0}", req);
        flightEmitter.send(req);
    }

    public void sendAccommodationBookingRequest(@ObservesAsync AccommodationBookingRequest req) {
        Log.infov("<< {0}", req);
        accommodationEmitter.send(req);
    }

    public void sendCarRentalBookingRequest(@ObservesAsync CarRentalBookingRequest req) {
        Log.infov("<< {0}", req);
        carRentalEmitter.send(req);
    }
}
