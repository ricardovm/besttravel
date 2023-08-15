package dev.ricardovm.besttravel.bookingservice.domain;

public interface BookingRepository {
    Booking findById(String bookingId);

    Booking save(Booking booking);
}
