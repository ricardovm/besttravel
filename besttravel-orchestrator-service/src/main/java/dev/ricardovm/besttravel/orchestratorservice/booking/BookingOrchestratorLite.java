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

@ApplicationScoped
public class BookingOrchestratorLite {

    @Inject
    Event<FlightBookingRequest> flightEvent;

    @Inject
    Event<AccommodationBookingRequest> accommodationEvent;

    @Inject
    Event<CarRentalBookingRequest> carRentalEvent;

    public void route(@ObservesAsync BookingCommandDTO cmd) {
        Log.infov(">> routing bookingId={0}", cmd.bookingId());
        if (cmd.flight() != null) {
            flightEvent.fireAsync(new FlightBookingRequest(cmd.bookingId(), cmd.quoteId(), cmd.flight()));
        }
        if (cmd.accommodation() != null) {
            accommodationEvent.fireAsync(new AccommodationBookingRequest(cmd.bookingId(), cmd.quoteId(), cmd.accommodation()));
        }
        if (cmd.carRental() != null) {
            carRentalEvent.fireAsync(new CarRentalBookingRequest(cmd.bookingId(), cmd.quoteId(), cmd.carRental()));
        }
    }
}
