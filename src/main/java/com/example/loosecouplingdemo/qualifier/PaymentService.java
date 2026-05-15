package com.example.loosecouplingdemo.qualifier;

public interface PaymentService {
    String processPayment(String orderId, double amount);
}
