package com.example.loosecouplingdemo.qualifier;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component("razorpayPaymentService")
@Primary
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
