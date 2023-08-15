package dev.ricardovm.besttravel.front.ui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.Route;
import dev.ricardovm.besttravel.front.services.booking.BookingService;
import dev.ricardovm.besttravel.front.services.booking.dto.request.AccommodationBookingRequestDTO;
import dev.ricardovm.besttravel.front.services.booking.dto.request.BookingRequestDTO;
import dev.ricardovm.besttravel.front.services.booking.dto.request.CarRentalBookingRequestDTO;
import dev.ricardovm.besttravel.front.services.booking.dto.request.FlightBookingRequestDTO;
import dev.ricardovm.besttravel.front.services.booking.dto.response.BookingResponseDTO;
import dev.ricardovm.besttravel.front.services.quote.QuoteService;
import dev.ricardovm.besttravel.front.services.quote.dto.request.AccommodationQuoteRequestDTO;
import dev.ricardovm.besttravel.front.services.quote.dto.request.CarRentalQuoteRequestDTO;
import dev.ricardovm.besttravel.front.services.quote.dto.request.FlightQuoteRequestDTO;
import dev.ricardovm.besttravel.front.services.quote.dto.request.QuoteRequestDTO;
import dev.ricardovm.besttravel.front.services.quote.dto.response.AccommodationQuoteResponseDTO;
import dev.ricardovm.besttravel.front.services.quote.dto.response.CarRentalQuoteResponseDTO;
import dev.ricardovm.besttravel.front.services.quote.dto.response.FlightQuoteResponseDTO;
import dev.ricardovm.besttravel.front.services.quote.dto.response.QuoteResponseDTO;
import jakarta.inject.Inject;

import java.time.LocalDate;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Consumer;

@Route("")
public class MainView extends VerticalLayout {
    @Inject
    QuoteService quoteService;

    @Inject
    BookingService bookingService;

    private String quoteId;
    private final ConcurrentMap<String, FlightQuoteResponseDTO> flightQuotes = new ConcurrentHashMap<>();
    private final ListDataProvider<FlightQuoteResponseDTO> flightQuotesProvider = new ListDataProvider<>(flightQuotes.values());
    private final ConcurrentMap<String, AccommodationQuoteResponseDTO> accommodationQuotes = new ConcurrentHashMap<>();
    private final ListDataProvider<AccommodationQuoteResponseDTO> accommodationQuotesProvider = new ListDataProvider<>(accommodationQuotes.values());
    private final ConcurrentMap<String, CarRentalQuoteResponseDTO> carRentalQuotes = new ConcurrentHashMap<>();
    private final ListDataProvider<CarRentalQuoteResponseDTO> carRentalQuotesProvider = new ListDataProvider<>(carRentalQuotes.values());

    private final Checkbox flightCheckBox = new Checkbox("Flight", true);
    private final Checkbox accommodationCheckBox = new Checkbox("Accommodation", true);
    private final Checkbox carRentalCheckBox = new Checkbox("Car rental", true);

    private final Button quoteButton = new Button("Quote");
    private final Button bookButton = new Button("Book now!");

    private final VerticalLayout quoteLayout = new VerticalLayout();

    private final VerticalLayout flightsLayout = new VerticalLayout();
    private final RadioButtonGroup<FlightQuoteResponseDTO> flightOptionsGroup = new RadioButtonGroup<>();

    private final VerticalLayout accommodationsLayout = new VerticalLayout();
    private final RadioButtonGroup<AccommodationQuoteResponseDTO> accommodationOptionsGroup = new RadioButtonGroup<>();

    private final VerticalLayout carRentalsLayout = new VerticalLayout();
    private final RadioButtonGroup<CarRentalQuoteResponseDTO> carRentalOptionsGroup = new RadioButtonGroup<>();

    private final VerticalLayout bookingLayout = new VerticalLayout();

    public MainView() {
        var title = new H1("Best Travel");

        var departureField = new TextField("Departure");
        departureField.setValue("Curitiba");
        departureField.setReadOnly(true);

        var destinationField = new TextField("Destination");
        destinationField.setValue("Amsterdam");
        destinationField.setReadOnly(true);

        var travelCitiesLayout = new HorizontalLayout(departureField, destinationField);

        var departureDateField = new DatePicker("Departure date");
        departureDateField.setValue(LocalDate.now().plusDays(7));
        departureDateField.setReadOnly(true);

        var returnDateField = new DatePicker("Return date");
        returnDateField.setValue(LocalDate.now().plusDays(14));
        returnDateField.setReadOnly(true);

        var travelDatesLayout = new HorizontalLayout(departureDateField, returnDateField);

        flightCheckBox.addValueChangeListener(e -> refreshQuoteButton());
        accommodationCheckBox.addValueChangeListener(e -> refreshQuoteButton());
        carRentalCheckBox.addValueChangeListener(e -> refreshQuoteButton());

        var optionsLayout = new HorizontalLayout(flightCheckBox, accommodationCheckBox, carRentalCheckBox);
        optionsLayout.setWidth(500f, Unit.PIXELS);
        optionsLayout.setAlignItems(Alignment.CENTER);

        quoteButton.addClickListener(e -> retuestQuote());
        quoteButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        bookButton.addClickListener(e -> requestBooking());
        bookButton.setEnabled(false);
        bookButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        var buttonsLayout = new HorizontalLayout(quoteButton, bookButton);

        flightsLayout.add(new H2("Flights"), flightOptionsGroup);
        flightsLayout.setWidth(800f, Unit.PIXELS);
        flightsLayout.setVisible(false);
        flightOptionsGroup.setItems(flightQuotesProvider);
        flightOptionsGroup.setItemLabelGenerator(flight ->
                "$%s: %s - From: %s To: %s - Departure: %s  Return: %s".formatted(
                        flight.price(),
                        flight.flighCompany(),
                        flight.origin(),
                        flight.destination(),
                        flight.departureDate(),
                        flight.returnDate())
        );
        flightOptionsGroup.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);

        accommodationsLayout.add(new H2("Accommodations"), accommodationOptionsGroup);
        accommodationsLayout.setWidth(800f, Unit.PIXELS);
        accommodationsLayout.setVisible(false);
        accommodationOptionsGroup.setItems(accommodationQuotesProvider);
        accommodationOptionsGroup.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        accommodationOptionsGroup.setItemLabelGenerator(accommodation ->
                "$%s - %s - %s - %s".formatted(
                        accommodation.price(),
                        accommodation.hotel(),
                        accommodation.checkInDate(),
                        accommodation.checkOutDate()));


        carRentalsLayout.add(new H2("Car rentals"), carRentalOptionsGroup);
        carRentalsLayout.setWidth(800f, Unit.PIXELS);
        carRentalsLayout.setVisible(false);
        carRentalOptionsGroup.setItems(carRentalQuotesProvider);
        carRentalOptionsGroup.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        carRentalOptionsGroup.setItemLabelGenerator(carRental ->
                "$%s - %s (%s) - %s (%s) to %s (%s)".formatted(
                        carRental.price(),
                        carRental.model(),
                        carRental.rentalCompany(),
                        carRental.pickupLocation(),
                        carRental.pickupDate(),
                        carRental.dropOffLocation(),
                        carRental.dropOffDate()));

        flightOptionsGroup.addValueChangeListener(e -> refreshBookButton());
        accommodationOptionsGroup.addValueChangeListener(e -> refreshBookButton());
        carRentalOptionsGroup.addValueChangeListener(e -> refreshBookButton());

        quoteLayout.add(flightsLayout, accommodationsLayout, carRentalsLayout);
        quoteLayout.setVisible(false);

        bookingLayout.setVisible(false);

        add(title,
                travelCitiesLayout,
                travelDatesLayout,
                optionsLayout,
                buttonsLayout,
                quoteLayout,
                bookingLayout);
    }

    private void retuestQuote() {
        resetQuotes();
        sendQuoteRequest();
    }

    private void resetQuotes() {
        bookingLayout.setVisible(false);
        bookButton.setEnabled(false);

        quoteLayout.setVisible(true);

        flightsLayout.setVisible(flightCheckBox.getValue());
        flightQuotes.clear();
        flightQuotesProvider.refreshAll();

        accommodationsLayout.setVisible(accommodationCheckBox.getValue());
        accommodationQuotes.clear();
        accommodationQuotesProvider.refreshAll();

        carRentalsLayout.setVisible(carRentalCheckBox.getValue());
        carRentalQuotes.clear();
        carRentalQuotesProvider.refreshAll();
    }

    private void sendQuoteRequest() {
        final var originAirport = "CWB";
        final var destinationAirport = "AMS";
        final var destination = "Amsterdam";
        final var departureDate = LocalDate.now();
        final var returnDate = departureDate.plusDays(7);

        final var flight = new FlightQuoteRequestDTO(
                originAirport,
                destinationAirport,
                departureDate,
                returnDate);

        final var accommodation = new AccommodationQuoteRequestDTO(
                destination,
                departureDate,
                returnDate);

        final var carRental = new CarRentalQuoteRequestDTO(
                destination,
                destination,
                departureDate,
                returnDate);

        quoteId = UUID.randomUUID().toString();

        var quoteRequest = QuoteRequestDTO.newBuilder()
                .quoteId(quoteId)
                .flight(flightCheckBox.getValue() ? flight : null)
                .accommodation(accommodationCheckBox.getValue() ? accommodation : null)
                .carRental(carRentalCheckBox.getValue() ? carRental : null)
                .build();

        var ui = UI.getCurrent();
        Consumer<QuoteResponseDTO> callback = quoteResponseDTO -> ui.access(() -> addQuote(quoteResponseDTO));

        quoteService.sendQuoteRequest(quoteRequest, callback);
    }

    private void addQuote(QuoteResponseDTO quoteResponseDTO) {
        if (quoteResponseDTO.flight() != null) {
            addFlightQuote(quoteResponseDTO);
        }

        if (quoteResponseDTO.accommodation() != null) {
            addAccommodationQuote(quoteResponseDTO);
        }

        if (quoteResponseDTO.carRental() != null) {
            addCarRentalQuote(quoteResponseDTO);
        }
    }

    private void addFlightQuote(QuoteResponseDTO quoteResponseDTO) {
        flightQuotes.put(quoteResponseDTO.flight().id(), quoteResponseDTO.flight());
        flightQuotesProvider.refreshAll();
    }

    private void addAccommodationQuote(QuoteResponseDTO quoteResponseDTO) {
        accommodationQuotes.put(quoteResponseDTO.accommodation().id(), quoteResponseDTO.accommodation());
        accommodationQuotesProvider.refreshAll();
    }

    private void addCarRentalQuote(QuoteResponseDTO quoteResponseDTO) {
        carRentalQuotes.put(quoteResponseDTO.carRental().id(), quoteResponseDTO.carRental());
        carRentalQuotesProvider.refreshAll();
    }

    private void requestBooking() {
        resetBookings();
        sendBookingRequest();
    }

    private void resetBookings() {
        bookButton.setEnabled(false);
        quoteLayout.setVisible(false);
        bookingLayout.setVisible(true);
        bookingLayout.removeAll();
    }

    private void sendBookingRequest() {
        final var flight = flightOptionsGroup.getValue();
        final var accommodation = accommodationOptionsGroup.getValue();
        final var carRental = carRentalOptionsGroup.getValue();

        var bookingRequestBuilder = BookingRequestDTO.newBuilder()
                .quoteId(quoteId)
                .bookingId(UUID.randomUUID().toString());

        if (flight != null) {
            var flightRequest = FlightBookingRequestDTO.newBuilder()
                    .id(flight.id())
                    .flighCompany(flight.flighCompany())
                    .origin(flight.origin())
                    .destination(flight.destination())
                    .departureDate(flight.departureDate())
                    .returnDate(flight.returnDate())
                    .price(flight.price())
                    .build();
            bookingRequestBuilder.flight(flightRequest);
        }

        if (accommodation != null) {
            var accommodationRequest = AccommodationBookingRequestDTO.newBuilder()
                    .id(accommodation.id())
                    .hotel(accommodation.hotel())
                    .city(accommodation.city())
                    .checkInDate(accommodation.checkInDate())
                    .checkOutDate(accommodation.checkOutDate())
                    .price(accommodation.price())
                    .build();
            bookingRequestBuilder.accommodation(accommodationRequest);
        }

        if (carRental != null) {
            var carRentalRequest = CarRentalBookingRequestDTO.newBuilder()
                    .id(carRental.id())
                    .model(carRental.model())
                    .rentalCompany(carRental.rentalCompany())
                    .pickupLocation(carRental.pickupLocation())
                    .dropOffLocation(carRental.dropOffLocation())
                    .pickupDate(carRental.pickupDate())
                    .dropOffDate(carRental.dropOffDate())
                    .price(carRental.price())
                    .build();
            bookingRequestBuilder.carRental(carRentalRequest);
        }

        var ui = UI.getCurrent();
        Consumer<BookingResponseDTO> callback = bookingResponse -> ui.access(() -> addBooking(bookingResponse));

        bookingService.sendBookingRequest(bookingRequestBuilder.build(), callback);
    }

    private void addBooking(BookingResponseDTO bookingResponse) {
        if (bookingResponse.flight() != null) {
            bookingLayout.add(new Paragraph("Flight: " + bookingResponse.flight().status()));
        }

        if (bookingResponse.accommodation() != null) {
            bookingLayout.add(new Paragraph("Accommodation: " + bookingResponse.accommodation().status()));
        }

        if (bookingResponse.carRental() != null) {
            bookingLayout.add(new Paragraph("Car rental: " + bookingResponse.carRental().status()));
        }
    }

    private void refreshQuoteButton() {
        quoteButton.setEnabled(
                flightCheckBox.getValue() ||
                        accommodationCheckBox.getValue() ||
                        carRentalCheckBox.getValue());
    }

    private void refreshBookButton() {
        bookButton.setEnabled(
                ((flightsLayout.isVisible() && flightOptionsGroup.getValue() != null) || !flightsLayout.isVisible()) &&
                        ((accommodationsLayout.isVisible() && accommodationOptionsGroup.getValue() != null) || !accommodationsLayout.isVisible()) &&
                        ((carRentalsLayout.isVisible() && carRentalOptionsGroup.getValue() != null) || !carRentalsLayout.isVisible()));
    }
}
