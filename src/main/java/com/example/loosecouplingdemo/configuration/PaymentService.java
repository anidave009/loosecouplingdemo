package com.example.loosecouplingdemo.configuration;

public interface PaymentService {
    String processPayment(String orderId, double amount);
}
