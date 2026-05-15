package com.example.loosecouplingdemo.tightcoupling;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class HardcodedRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("========================================");
        System.out.println("  STEP 1 : Hardcoded / Tight Coupling  ");
        System.out.println("========================================\n");

        OrderService orderService = new OrderService();
        orderService.placeOrder("ORD-1001", 4999.00);

        System.out.println("Problem : Switching payment gateway");
        System.out.println("requires direct changes inside OrderService.");
        System.out.println("This violates Open/Closed Principle.\n");
    }
}
