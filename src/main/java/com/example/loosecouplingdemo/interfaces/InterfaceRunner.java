package com.example.loosecouplingdemo.interfaces;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class InterfaceRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("==========================================");
        System.out.println("  STEP 2 : Loose Coupling via Interface  ");
        System.out.println("==========================================\n");

        //  Swap the gateway by just changing what you pass in
        // OrderService has NO idea which gateway it's using
        System.out.println("--- Using Razorpay ---");
        OrderService razorpayOrder = new OrderService(new RazorpayPaymentService());
        razorpayOrder.placeOrder("ORD-2001", 4999.00);

        System.out.println("--- Switched to Stripe (zero changes to OrderService!) ---");
        OrderService stripeOrder = new OrderService(new StripePaymentService());
        stripeOrder.placeOrder("ORD-2002", 79.99);

        System.out.println("Fix     : OrderService now depends on PaymentService interface.");
        System.out.println("Benefit : Swap any gateway without modifying OrderService.");
        System.out.println("Problem : We are still manually creating objects with 'new'.");
        System.out.println("Next    : Spring IoC will handle object creation for us.\n");

    }
}
