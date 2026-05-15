package com.example.loosecouplingdemo.configuration;

//Plain Java class — no @Service
// PaymentConfig creates and wires this

public class OrderService {
    private final PaymentService paymentService;

    public OrderService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public void placeOrder(String orderId, double amount) {
        System.out.println("  OrderService : Initiating order -> " + orderId);
        String status=paymentService.processPayment(orderId, amount);
        System.out.println("  OrderService : Order complete. Status -> " + status + "\n");    }
}
