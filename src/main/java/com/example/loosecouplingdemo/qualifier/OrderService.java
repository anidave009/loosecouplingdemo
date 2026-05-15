package com.example.loosecouplingdemo.qualifier;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final PaymentService defaultPaymentService;
    private final PaymentService stripePaymentService;
    private final PaymentService paypalPaymentService;


    public OrderService( PaymentService defaultPaymentService,
                         @Qualifier("stripePaymentService") PaymentService stripePaymentService,
                         @Qualifier("paypalPaymentService") PaymentService paypalPaymentService
                         ) {
        this.defaultPaymentService = defaultPaymentService;
        this.stripePaymentService  = stripePaymentService;
        this.paypalPaymentService  = paypalPaymentService;
    }

    public void placeOrder(String orderId, double amount,String gateway) {
        System.out.println("  OrderService : Initiating order -> " + orderId);
        PaymentService selectedGateway = switch (gateway.toLowerCase()) {
            case "stripe" -> stripePaymentService;
            case "paypal" -> paypalPaymentService;
            default       -> defaultPaymentService; // Razorpay
        };

        String status = selectedGateway.processPayment(orderId, amount);
        System.out.println("  OrderService : Order complete. Status -> " + status + "\n");    }
}
