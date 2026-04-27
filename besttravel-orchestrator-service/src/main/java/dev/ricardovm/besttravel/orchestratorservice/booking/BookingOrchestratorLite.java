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

    public void route(@ObservesAsync BookingCommandDTO bookingCommand) {
        Log.infov(">> routing bookingId={0}", bookingCommand.bookingId());

        if (bookingCommand.flight() != null) {
            flightEvent.fireAsync(new FlightBookingRequest(bookingCommand.bookingId(), bookingCommand.quoteId(), bookingCommand.flight()));
        }

        if (bookingCommand.accommodation() != null) {
            accommodationEvent.fireAsync(new AccommodationBookingRequest(bookingCommand.bookingId(), bookingCommand.quoteId(), bookingCommand.accommodation()));
        }

        if (bookingCommand.carRental() != null) {
            carRentalEvent.fireAsync(new CarRentalBookingRequest(bookingCommand.bookingId(), bookingCommand.quoteId(), bookingCommand.carRental()));
        }
    }
}
