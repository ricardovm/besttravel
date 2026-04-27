package dev.ricardovm.besttravel.orchestratorservice.quote;

import io.quarkus.logging.Log;
import io.smallrye.reactive.messaging.kafka.api.IncomingKafkaRecordMetadata;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;

import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class QuoteDlqMonitor {

    @Incoming("quote-commands-dlq")
    public CompletionStage<Void> onQuoteCommandDlq(Message<String> message) {
        logDlq("quote-commands-dlq", message);
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
