package com.example.loosecouplingdemo.profiles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("profileOrderService")
public class OrderService {
    private final PaymentService paymentService;

    @Autowired
    public OrderService( @Qualifier("profilePaymentService")PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public void placeOrder(String orderId, double amount) {
        System.out.println("  OrderService : Initiating order -> " + orderId);
        String status=paymentService.processPayment(orderId, amount);
        System.out.println("  OrderService : Order complete. Status -> " + status + "\n");    }
}
