package com.payflowx.payment_service.consumer;

import com.payflowx.common.event.PaymentApprovedEvent;
import com.payflowx.common.event.PaymentCreatedEvent;
import com.payflowx.common.event.PaymentRejectedEvent;
import com.payflowx.payment_service.entity.Payment;
import com.payflowx.payment_service.enums.PaymentStatus;
import com.payflowx.payment_service.exception.PaymentNotFoundException;
import com.payflowx.payment_service.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentStatusConsumer {

    private final PaymentRepository paymentRepository;


    @KafkaListener(topics = "payment-approved", groupId = "payment-service")
    public void updateApprovedPayment(PaymentApprovedEvent event) {
        Payment payment=paymentRepository.findById(event.paymentId()).orElseThrow((() -> new PaymentNotFoundException(event.paymentId())));
        payment.setStatus(PaymentStatus.APPROVED);
        paymentRepository.save(payment);
    }

    @KafkaListener(topics = "payment-rejected", groupId = "payment-service")
    public void updateRejectedPayment(PaymentRejectedEvent event) {
        Payment payment=paymentRepository.findById(event.paymentId()).orElseThrow((() -> new PaymentNotFoundException(event.paymentId())));
        payment.setStatus(PaymentStatus.DECLINED);
        paymentRepository.save(payment);
        log.info("Rejected Payment: {}", event);
    }
}
