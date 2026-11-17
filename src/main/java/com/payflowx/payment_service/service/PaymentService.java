package com.payflowx.payment_service.service;

import com.payflowx.common.event.PaymentCreatedEvent;
import com.payflowx.payment_service.dto.CreatePaymentRequest;
import com.payflowx.payment_service.dto.PaymentResponse;
import com.payflowx.payment_service.entity.Payment;
import com.payflowx.payment_service.enums.PaymentStatus;
import com.payflowx.payment_service.exception.PaymentNotFoundException;
import com.payflowx.payment_service.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final KafkaTemplate<String,PaymentCreatedEvent> kafkaTemplate;

    public PaymentResponse createPayment(CreatePaymentRequest request) {
        Payment payment = Payment.builder()
                .amount(request.amount())
                .currency(request.currency().toUpperCase())
                .customerEmail(request.customerEmail())
                .description(request.description())
                .status(PaymentStatus.PENDING)
                .build();

        Payment savedPayment = paymentRepository.save(payment);
        PaymentCreatedEvent event=toPaymentEvent(savedPayment);
        kafkaTemplate.send("payment-created", savedPayment.getId().toString(), event);
        return toPaymentResponse(savedPayment);
    }

    private PaymentCreatedEvent toPaymentEvent(Payment payment) {
        return new PaymentCreatedEvent( payment.getId(),
                payment.getAmount(),
                payment.getCurrency(),
                payment.getCustomerEmail(),
                payment.getStatus().toString(),
                payment.getCreatedAt());
    }

    public PaymentResponse getPayment(UUID id) {
        Payment payment=paymentRepository.findById(id).orElseThrow((() -> new PaymentNotFoundException(id)));
        return toPaymentResponse(payment);
    }

    public PaymentResponse toPaymentResponse(Payment payment){
        return new PaymentResponse( payment.getId(),
                payment.getAmount(),
                payment.getCurrency(),
                payment.getStatus(),
                payment.getCustomerEmail(),
                payment.getDescription(),
                payment.getCreatedAt());
    }

    public List<PaymentResponse> getPayments() {
        List<Payment> payments= paymentRepository.findAll();
        return payments.stream().map(this::toPaymentResponse
        ).toList();
    }

    public Boolean deleteAllPayments(){
         paymentRepository.deleteAll();
         int paymentsCount =paymentRepository.findAll().size();
         return paymentsCount == 0;
    }
}