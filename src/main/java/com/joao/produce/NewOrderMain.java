package com.joao.produce;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {
    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {
        try(var dispatcher = new KafkaDispatcher()) {
            for (int i = 0; i < 10; i++) {
                var key = UUID.randomUUID().toString();
                var value = key + ",45546,7825654469";
                dispatcher.send("ECOMMERCE_NEW_ORDER", key, value);
        
                var email = "Thank you for order! We are processing your order!";
                dispatcher.send("ECOMMERCE_SEND_EMAIL", key, value);
            }
        }
    }
}
