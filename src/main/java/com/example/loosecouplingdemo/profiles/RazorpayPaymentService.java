package com.example.loosecouplingdemo.profiles;


import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component("profilePaymentService")
@Profile("prod")
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
