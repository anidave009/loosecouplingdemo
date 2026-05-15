package com.example.loosecouplingdemo.profiles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(6)
public class ProfileRunner implements CommandLineRunner {

    private final OrderService orderService;

    @Autowired
    public ProfileRunner(
            @Qualifier("profileOrderService") OrderService orderService
    ) {
        this.orderService = orderService;
    }

    @Override
    public void run(String... args) {
        System.out.println("==========================================");
        System.out.println("  STEP 6 : @Profile                     ");
        System.out.println("==========================================\n");

        System.out.println("Active profile drives which");
        System.out.println("PaymentService Spring injects.\n");

        orderService.placeOrder("ORD-6001", 5999.00);

        System.out.println("Switch profile in application.properties.");
        System.out.println("Zero code changes needed.\n");
    }
}