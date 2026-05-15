package com.example.loosecouplingdemo.configuration;


// Plain Java class — no Spring annotation
// Spring has no idea this exists until PaymentConfig tells it
public class RazorpayPaymentService implements PaymentService {

    @Override
    public String processPayment(String orderId, double amount) {
        System.out.println("  [Razorpay] Processing payment...");
        System.out.println("  [Razorpay] Order ID : " + orderId);
        System.out.println("  [Razorpay] Amount   : ₹" + amount);
        System.out.println("  [Razorpay] Status   : SUCCESS");
        return "SUCCESS";
    }
}
