package com.example.loosecouplingdemo.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(5)
public class ConfigurationRunner implements CommandLineRunner {

    //  Spring finds OrderService bean from PaymentConfig and injects it
    private final OrderService orderService;

    @Autowired
    public ConfigurationRunner(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public void run(String... args) {
        System.out.println("==========================================");
        System.out.println("  STEP 5 : @Configuration + @Bean        ");
        System.out.println("==========================================\n");

        System.out.println("Notice  : No @Component on any service class.");
        System.out.println("Notice  : PaymentConfig owns ALL wiring.\n");

        orderService.placeOrder("ORD-5001", 12999.00);

        System.out.println("Fix     : One config class controls everything.");
        System.out.println("Benefit : Switch gateway by changing ONE line");
        System.out.println("          in PaymentConfig. Nothing else changes.");
        System.out.println("Next    : @Profile for environment-based switching.\n");
    }
}
