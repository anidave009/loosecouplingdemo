package com.example.loosecouplingdemo.qualifier;

import org.springframework.stereotype.Component;

@Component("paypalPaymentService")
public class PaypalPaymentService implements PaymentService {
    @Override
    public String processPayment(String orderId, double amount) {
        System.out.println("  [PayPal] Processing payment...");
        System.out.println("  [PayPal] Order ID : " + orderId);
        System.out.println("  [PayPal] Amount   : $" + amount);
        System.out.println("  [PayPal] Status   : SUCCESS");
        return "SUCCESS";
    }
}
