package dev.ricardovm.besttravel.bookingservice.services;

import dev.ricardovm.besttravel.bookingservice.domain.*;
import dev.ricardovm.besttravel.bookingservice.services.dto.request.BookingRequestDTO;
import dev.ricardovm.besttravel.bookingservice.services.dto.response.BookingResponseDTO;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

@ApplicationScoped
public class BookingInformationService {
    @Inject
    BookingRepository repository;

    public void receiveBookingRequest(@Observes BookingRequestDTO bookingRequest) {
        Log.infov(">> {0}", bookingRequest);

        var booking = new Booking();
        booking.setId(bookingRequest.bookingId());
        booking.setQuoteId(bookingRequest.quoteId());

        if (bookingRequest.flight() != null) {
            final var flight = getFlight(bookingRequest);
            booking.setFlight(flight);
        }

        if (bookingRequest.accommodation() != null) {
            final var accommodation = getAccommodation(bookingRequest);
            booking.setAccommodation(accommodation);
        }

        if (bookingRequest.carRental() != null) {
            final var carRental = getCarRental(bookingRequest);
            booking.setCarRental(carRental);
        }

        repository.save(booking);
    }

    private static Flight getFlight(BookingRequestDTO bookingRequest) {
        var flightRequest = bookingRequest.flight();

        var flight = new Flight();
        flight.setId(flightRequest.id());
        flight.setCompany(flightRequest.flighCompany());
        flight.setOrigin(flightRequest.origin());
        flight.setDestination(flightRequest.destination());
        flight.setDepartureDate(flightRequest.departureDate());
        flight.setReturnDate(flightRequest.returnDate());
        flight.setPrice(flightRequest.price());

        return flight;
    }

    private static Accommodation getAccommodation(BookingRequestDTO bookingRequest) {
        var accommodationRequest = bookingRequest.accommodation();

        var accommodation = new Accommodation();
        accommodation.setId(accommodationRequest.id());
        accommodation.setHotel(accommodationRequest.hotel());
        accommodation.setCity(accommodationRequest.city());
        accommodation.setCheckInDate(accommodationRequest.checkInDate());
        accommodation.setCheckOutDate(accommodationRequest.checkOutDate());
        accommodation.setPrice(accommodationRequest.price());

        return accommodation;
    }

    private static CarRental getCarRental(BookingRequestDTO bookingRequest) {
        var carRentalRequest = bookingRequest.carRental();

        var carRental = new CarRental();
        carRental.setId(carRentalRequest.id());
        carRental.setRentalCompany(carRentalRequest.rentalCompany());
        carRental.setModel(carRentalRequest.model());
        carRental.setPickupLocation(carRentalRequest.pickupLocation());
        carRental.setPickupDate(carRentalRequest.pickupDate());
        carRental.setDropOffLocation(carRentalRequest.dropOffLocation());
        carRental.setDropOffDate(carRentalRequest.dropOffDate());
        carRental.setPrice(carRentalRequest.price());

        return carRental;
    }

    public void receiveBookingResponse(@Observes BookingResponseDTO bookingResponse) {
        Log.infov(">> {0}", bookingResponse);

        var booking = repository.findById(bookingResponse.bookingId());

        if (booking == null) {
            Log.warn("Booking not found");
            return;
        }

        if (bookingResponse.flight() != null) {
            Log.infov("Setting flight status of {0} to {1}", booking.getId(), bookingResponse.flight().status());
            booking.getFlight().setStatus(Status.valueOf(bookingResponse.flight().status()));
        }

        if (bookingResponse.accommodation() != null) {
            Log.infov("Setting accommodation status of {0} to {1}", booking.getId(), bookingResponse.accommodation().status());
            booking.getAccommodation().setStatus(Status.valueOf(bookingResponse.accommodation().status()));
        }

        if (bookingResponse.carRental() != null) {
            Log.infov("Setting car rental status of {0} to {1}", booking.getId(), bookingResponse.carRental().status());
            booking.getCarRental().setStatus(Status.valueOf(bookingResponse.carRental().status()));
        }
    }
}
