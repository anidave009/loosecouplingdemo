package com.example.loosecouplingdemo.tightcoupling;

public class OrderService {
    RazorPaymentService paymentService = new RazorPaymentService();
    public void placeOrder(String orderId , double amount){
        System.out.println("OrderService : Initiating order -> " + orderId);
        paymentService.processPayment(orderId, amount);
        System.out.println("OrderService : Order placed successfully!\n");
    }
}
