package dev.ricardovm.besttravel.front.services.common;

import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;

public class TimedCallbackRegistry<T> {
    private final ConcurrentMap<String, CallbackEntry<T>> callbacks = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler;
    private final Function<String, T> timeoutResponseFactory;
    private final Duration ttl;

    public TimedCallbackRegistry(Duration ttl, Function<String, T> timeoutResponseFactory) {
        this.ttl = ttl;
        this.timeoutResponseFactory = timeoutResponseFactory;
        scheduler = Executors.newSingleThreadScheduledExecutor(new CallbackThreadFactory());
    }

    public void shutdown() {
        scheduler.shutdown();
    }

    public void register(String id, Consumer<T> callback) {
        if (callback == null) {
            return;
        }

        var entry = new CallbackEntry<>(callback);
        var previous = callbacks.put(id, entry);

        if (previous != null) {
            previous.close();
        }

        entry.scheduleExpiration(scheduler.schedule(
                () -> expire(id, entry),
                ttl.toMillis(),
                TimeUnit.MILLISECONDS));
    }

    public boolean deliver(String id, T response) {
        var entry = callbacks.get(id);

        if (entry == null) {
            return false;
        }

        entry.deliver(response);

        return true;
    }

    private void expire(String id, CallbackEntry<T> entry) {
        if (callbacks.remove(id, entry)) {
            entry.expire(timeoutResponseFactory.apply(id));
        }
    }

    private static final class CallbackThreadFactory implements ThreadFactory {
        @Override
        public Thread newThread(Runnable runnable) {
            var thread = new Thread(runnable, "timed-callback-registry");
            thread.setDaemon(true);
            return thread;
        }
    }

    private static final class CallbackEntry<T> {
        private final Consumer<T> callback;
        private boolean closed;
        private ScheduledFuture<?> expirationTask;

        private CallbackEntry(Consumer<T> callback) {
            this.callback = callback;
        }

        private synchronized void scheduleExpiration(ScheduledFuture<?> expirationTask) {
            this.expirationTask = expirationTask;
        }

        private synchronized void deliver(T response) {
            if (!closed) {
                callback.accept(response);
            }
        }

        private synchronized void expire(T timeoutResponse) {
            if (closed) {
                return;
            }

            closed = true;
            cancelExpiration();
            callback.accept(timeoutResponse);
        }

        private synchronized void close() {
            closed = true;
            cancelExpiration();
        }

        private void cancelExpiration() {
            if (expirationTask != null) {
                expirationTask.cancel(false);
            }
        }
    }
}
