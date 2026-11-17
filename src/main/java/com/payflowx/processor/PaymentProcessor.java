package com.payflowx.processor;

import com.payflowx.common.event.PaymentApprovedEvent;
import com.payflowx.common.event.PaymentProcessedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentProcessor {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(topics = "payment-approved", groupId = "processor-service")
    public void handlePaymentApproved(PaymentApprovedEvent event) {
        log.info("Sending payment {} to gateway", event.paymentId());

        CompletableFuture.supplyAsync(() -> callGateway(event))
                .thenAccept(success -> publishResult(event, success));
    }

    /*@KafkaListener(topics = "payment-approved", groupId = "processor-service")
    public void handlePaymentApproved(PaymentApprovedEvent event) {
        log.info("Sending payment {} to gateway", event.paymentId());

        boolean success=callGateway(event);
        publishResult(event,success);
    }*/

    private boolean callGateway(PaymentApprovedEvent event) {
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return ThreadLocalRandom.current().nextInt(100) < 90;
    }

    private void publishResult(PaymentApprovedEvent event, boolean success) {
        PaymentProcessedEvent processedEvent = new PaymentProcessedEvent(
                event.paymentId(),
                success,
                success ? null : "Gateway declined the transaction",
                LocalDateTime.now());

        kafkaTemplate.send("payment-processed", event.paymentId().toString(), processedEvent);
        log.info("Published payment-processed for {} success={}", event.paymentId(), success);
    }
}
