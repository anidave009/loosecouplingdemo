package com.example.loosecouplingdemo.springioc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service("springIocOrderService")
public class OrderService {
    private final PaymentService paymentService;

    @Autowired
    public OrderService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public void placeOrder(String orderId, double amount) {
        System.out.println("  OrderService : Initiating order -> " + orderId);
        String status=paymentService.processPayment(orderId, amount);
        System.out.println("  OrderService : Order complete. Status -> " + status + "\n");    }
}
