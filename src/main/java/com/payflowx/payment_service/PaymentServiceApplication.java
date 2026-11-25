package com.payflowx.payment_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

// This class sits in com.payflowx.payment_service, but code lives across all of com.payflowx.
// Each scan only looks inside its own package by default, so all three are widened.
@SpringBootApplication
@ComponentScan(basePackages = "com.payflowx")          // services, controllers, filters
@EnableJpaRepositories(basePackages = "com.payflowx")  // repositories (e.g. UserRepo)
@EntityScan(basePackages = "com.payflowx")             // @Entity classes (e.g. User)
public class PaymentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentServiceApplication.class, args);
	}

}
