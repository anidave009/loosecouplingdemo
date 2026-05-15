package com.example.loosecouplingdemo.profiles;


import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component("profilePaymentService")
@Profile("dev")
public class MockPaymentService implements PaymentService {
    @Override
    public String processPayment(String orderId, double amount) {
        System.out.println("  [MOCK] No real payment processed.");
        System.out.println("  [MOCK] Order ID : " + orderId);
        System.out.println("  [MOCK] Amount   : ₹" + amount);
        System.out.println("  [MOCK] Status   : SIMULATED SUCCESS");
        return "SIMULATED SUCCESS";
    }
}
