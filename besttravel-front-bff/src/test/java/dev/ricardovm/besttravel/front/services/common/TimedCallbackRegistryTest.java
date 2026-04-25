package dev.ricardovm.besttravel.front.services.common;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TimedCallbackRegistryTest {
    @Test
    void deliversAllResponsesUntilExpiry() {
        var received = new ArrayList<String>();
        var registry = new TimedCallbackRegistry<String>(Duration.ofSeconds(5), id -> "timeout:" + id);

        registry.register("quote-1", received::add);

        assertTrue(registry.deliver("quote-1", "first"));
        assertTrue(registry.deliver("quote-1", "second"));
        assertTrue(registry.deliver("quote-1", "third"));
        assertEquals(List.of("first", "second", "third"), received);
    }

    @Test
    void expiresCallbackWhenResponseDoesNotArrive() throws InterruptedException {
        var received = new ArrayList<String>();
        var registry = new TimedCallbackRegistry<String>(Duration.ofMillis(50), id -> "timeout:" + id);

        registry.register("quote-2", received::add);

        Thread.sleep(200L);

        assertEquals(List.of("timeout:quote-2"), received);
        assertFalse(registry.deliver("quote-2", "late"));
    }
}
