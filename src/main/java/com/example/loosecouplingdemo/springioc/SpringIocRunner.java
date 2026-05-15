package com.example.loosecouplingdemo.springioc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)
public class SpringIocRunner implements CommandLineRunner {

    private final OrderService orderService;

    @Autowired
    public SpringIocRunner(
            @Qualifier("springIocOrderService") OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("==========================================");
        System.out.println("   STEP 3 : Spring IoC / Injection       ");
        System.out.println("==========================================\n");

        System.out.println("Notice : No 'new' keyword anywhere.");
        System.out.println("Spring created OrderService and injected");
        System.out.println("Stripe Pmnt Service automatically.\n");

        orderService.placeOrder("ORD-3001", 8999.00);

        System.out.println("Problem : Two beans implement PaymentService.");
        System.out.println("Spring can't choose without guidance.");
        System.out.println("Next    : @Primary and @Qualifier solve this.\n");
    }
}
