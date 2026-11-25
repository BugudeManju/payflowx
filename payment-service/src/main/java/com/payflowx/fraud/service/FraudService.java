package com.payflowx.fraud.service;

import com.payflowx.common.event.PaymentApprovedEvent;
import com.payflowx.common.event.PaymentCreatedEvent;
import com.payflowx.common.event.PaymentRejectedEvent;
import com.payflowx.fraud.dto.FraudCheckResult;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class FraudService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${fraud.suspicious-amount-threshold}")
    private BigDecimal suspiciousThreshold;

    @Value("${blocked.email.domains}")
    private List<String> blockedDomains;

    @KafkaListener(topics = "payment-created", groupId = "fraud-service")
    public void handlePaymentCreated(PaymentCreatedEvent event) {
        FraudCheckResult result = evaluate(event);

        if (result.approved()) {
            PaymentApprovedEvent approvedEvent = new PaymentApprovedEvent(
                    event.paymentId(), event.amount(), event.currency(),
                    event.customerEmail(), LocalDateTime.now());
            kafkaTemplate.send("payment-approved", event.paymentId().toString(), approvedEvent);
        } else {
            PaymentRejectedEvent rejectedEvent = new PaymentRejectedEvent(
                    event.paymentId(), result.reason(), LocalDateTime.now());
            kafkaTemplate.send("payment-rejected", event.paymentId().toString(), rejectedEvent);
        }
    }

    public FraudCheckResult evaluate(PaymentCreatedEvent event) {
        if (event.amount().compareTo(suspiciousThreshold) > 0) {
            return FraudCheckResult.rejected("Amount exceeds threshold");
        }
        if (isBlockedEmailDomain(event.customerEmail())) {
            return FraudCheckResult.rejected("Blocked email domain");
        }
        return FraudCheckResult.approve();
    }

    private boolean isBlockedEmailDomain(String email) {
      return  blockedDomains.contains(email.substring(email.indexOf('@') + 1));
    }


}
