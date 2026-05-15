package com.example.loosecouplingdemo.tightcoupling;

public class RazorPaymentService {
    public void processPayment(String orderId, double amount){
        System.out.println("---- Razorpay Gateway ----");
        System.out.println("Processing payment for Order ID : " + orderId);
        System.out.println("Amount charged via Razorpay   : ₹" + amount);
        System.out.println("Status                        : SUCCESS");
        System.out.println("--------------------------");
    }
}
