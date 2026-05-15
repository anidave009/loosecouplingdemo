package com.example.loosecouplingdemo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

//  This class IS the wiring blueprint
// One place. All decisions. Nothing scattered.
@Configuration
public class PaymentConfig {
    //  Spring calls this method and registers the return value as a bean
    @Bean
    @Primary  // default PaymentService across the app
    public PaymentService configurationRazorpayPaymentService() {
        return new RazorpayPaymentService();
    }

    @Bean
    public PaymentService configurationStripePaymentService() {
        return new StripePaymentService();
    }

    @Bean
    public PaymentService configurationPaypalPaymentService() {
        return new PayPalPaymentService();
    }
    //  To switch the entire app from Razorpay to Stripe —
    // change ONE line here. Nothing else in the codebase changes.
    @Bean
    public OrderService configurationOrderService() {
        return new OrderService(configurationRazorpayPaymentService()); // ← change this line only
    }
}
