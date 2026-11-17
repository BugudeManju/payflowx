package com.payflowx.payment_service.consumer;

import com.payflowx.common.event.PaymentApprovedEvent;
import com.payflowx.common.event.PaymentProcessedEvent;
import com.payflowx.common.event.PaymentRejectedEvent;
import com.payflowx.payment_service.entity.Payment;
import com.payflowx.payment_service.enums.PaymentStatus;
import com.payflowx.payment_service.exception.PaymentNotFoundException;
import com.payflowx.payment_service.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentProcessorConsumer {
    private final PaymentRepository paymentRepository;


    @KafkaListener(topics = "payment-processed", groupId = "payment-service")
    public void updateApprovedPayment(PaymentProcessedEvent event) {
        Payment payment=paymentRepository.findById(event.paymentId()).orElseThrow((() -> new PaymentNotFoundException(event.paymentId())));
        payment.setStatus(event.success()?PaymentStatus.SUCCESS:PaymentStatus.FAILED);
        paymentRepository.save(payment);
    }


}
