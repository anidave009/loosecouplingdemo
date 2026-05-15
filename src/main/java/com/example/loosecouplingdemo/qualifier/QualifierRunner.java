package com.example.loosecouplingdemo.qualifier;

import com.example.loosecouplingdemo.qualifier.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(4)
public class QualifierRunner implements CommandLineRunner {

    private final OrderService orderService;

    @Autowired
    public QualifierRunner(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public void run(String... args) {
        System.out.println("==========================================");
        System.out.println("  STEP 4 : @Primary and @Qualifier       ");
        System.out.println("==========================================\n");

        System.out.println("--- Default gateway (@Primary = Razorpay) ---");
        orderService.placeOrder("ORD-4001", 4999.00, "razorpay");

        System.out.println("--- Explicit Stripe (@Qualifier) ---");
        orderService.placeOrder("ORD-4002", 79.99, "stripe");

        System.out.println("--- Explicit PayPal (@Qualifier) ---");
        orderService.placeOrder("ORD-4003", 199.00, "paypal");

        System.out.println("Fix     : @Primary sets the default bean.");
        System.out.println("Fix     : @Qualifier overrides and picks specific bean.");
        System.out.println("Benefit : All three gateways managed by Spring.");
        System.out.println("Next    : @Configuration + @Bean for manual wiring.\n");
    }
}