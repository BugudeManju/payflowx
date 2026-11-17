package com.payflowx.payment_service.controller;

import com.payflowx.payment_service.dto.CreatePaymentRequest;
import com.payflowx.payment_service.dto.PaymentResponse;
import com.payflowx.payment_service.entity.Payment;
import com.payflowx.payment_service.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentResponse createPayment(@Valid @RequestBody CreatePaymentRequest request) {
        return paymentService.createPayment(request);
    }

    @GetMapping("/{id}")
    public PaymentResponse getPayment(@PathVariable("id") UUID id){
        return paymentService.getPayment(id);
    }

    @GetMapping
    public List<PaymentResponse> getPayments(){
        return paymentService.getPayments();
    }

    @DeleteMapping
    private Boolean deleteAllPayments(){
        return paymentService.deleteAllPayments();
    }
}