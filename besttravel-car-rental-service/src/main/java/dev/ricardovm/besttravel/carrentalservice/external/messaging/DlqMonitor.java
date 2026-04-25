package dev.ricardovm.besttravel.carrentalservice.external.messaging;

import io.quarkus.logging.Log;
import io.smallrye.reactive.messaging.kafka.api.IncomingKafkaRecordMetadata;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;

import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class DlqMonitor {

    @Incoming("car-rental-quote-requests-dlq")
    public CompletionStage<Void> onQuoteDlq(Message<String> message) {
        logDlq("car-rental-quote-requests-dlq", message);
        return message.ack();
    }

    @Incoming("car-rental-booking-requests-dlq")
    public CompletionStage<Void> onBookingDlq(Message<String> message) {
        logDlq("car-rental-booking-requests-dlq", message);
        return message.ack();
    }

    private void logDlq(String topic, Message<String> message) {
        var reason = message.getMetadata(IncomingKafkaRecordMetadata.class)
                .map(m -> m.getHeaders().lastHeader("dead-letter-reason"))
                .map(h -> new String(h.value()))
                .orElse("unknown");
        Log.errorf("Message in DLQ [%s]. Reason: %s. Payload: %s", topic, reason, message.getPayload());
    }
}
