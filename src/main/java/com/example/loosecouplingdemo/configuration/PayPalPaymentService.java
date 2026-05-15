package com.example.loosecouplingdemo.configuration;

public class PayPalPaymentService implements PaymentService {

    @Override
    public String processPayment(String orderId, double amount) {
        System.out.println("  [PayPal] Processing payment...");
        System.out.println("  [PayPal] Order ID : " + orderId);
        System.out.println("  [PayPal] Amount   : $" + amount);
        System.out.println("  [PayPal] Status   : SUCCESS");
        return "SUCCESS";
    }
}
