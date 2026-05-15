package com.example.loosecouplingdemo.interfaces;

public interface PaymentService {
    String processPayment(String orderId, double amount);
}
