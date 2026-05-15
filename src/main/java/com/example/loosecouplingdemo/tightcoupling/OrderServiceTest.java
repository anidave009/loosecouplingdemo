package com.example.loosecouplingdemo.tightcoupling;

import org.testng.annotations.Test;

public class OrderServiceTest {

    @Test
    public void OrderServiceTest() {
        // No way to inject a mock here — that's exactly the problem!
        // We are forced to use the real RazorpayPaymentService
        OrderService orderService = new OrderService();

        // This calls real Razorpay logic — we can't control or replace it
        orderService.placeOrder("ORD-011",450.12);

        // No assertion possible on payment gateway behavior
        // We can only trust it ran without crashing
        System.out.println("Notice : We cannot mock or swap");
        System.out.println("RazorpayPaymentService here.");
        System.out.println("That's the core problem of tight coupling.");
    }
}
