package com.example.loosecouplingdemo.profiles;

public interface PaymentService {
    String processPayment(String orderId, double amount);
}
