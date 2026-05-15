package com.example.loosecouplingdemo.springioc;

public interface PaymentService {
    String processPayment(String orderId, double amount);
}
