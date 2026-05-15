package com.example.loosecouplingdemo.qualifier;

import org.springframework.stereotype.Component;

@Component("stripePaymentService")
public class StripePaymentService implements PaymentService {
    @Override
    public String processPayment(String orderId, double amount) {
        System.out.println("  [Stripe] Processing payment...");
        System.out.println("  [Stripe] Order ID : " + orderId);
        System.out.println("  [Stripe] Amount   : $" + amount);
        System.out.println("  [Stripe] Status   : SUCCESS");
        return "SUCCESS";
    }
}
