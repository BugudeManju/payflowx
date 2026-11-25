package com.payflowx.audit;

import com.payflowx.common.event.PaymentCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PaymentCreatedEventConsumer {

    @KafkaListener(topics = "payment-created", groupId = "audit-service")
    public void handlePaymentCreated(PaymentCreatedEvent event) {
        log.info("Received payment-created event: {}", event);
    }
}