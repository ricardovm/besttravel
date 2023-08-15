package dev.ricardovm.besttravel.bookingservice.external.db;

import dev.ricardovm.besttravel.bookingservice.domain.Booking;
import dev.ricardovm.besttravel.bookingservice.domain.BookingRepository;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@ApplicationScoped
public class BookingRepositoryImpl implements BookingRepository {
    private final ConcurrentMap<String, Booking> storage = new ConcurrentHashMap<>();

    @Override
    public Booking findById(String bookingId) {
        return storage.get(bookingId);
    }

    @Override
    public Booking save(Booking booking) {
        Log.infov("Saving booking {0}", booking.getId());
        return storage.put(booking.getId(), booking);
    }
}
