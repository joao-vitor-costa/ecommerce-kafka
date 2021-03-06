package com.joao.produce;


import com.joao.dto.Email;
import com.joao.dto.Order;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {
	public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {
		try (var orderDispatcher = new KafkaDispatcher<Order>()) {
			try (var emailDispatcher = new KafkaDispatcher<Email>()) {
				for (int i = 0; i < 10; i++) {
					var orderId = UUID.randomUUID().toString();
					var amount = new BigDecimal(Math.random() * 5000 + 1);
					var email = Math.random() + "@email.com";
					var order = new Order(orderId, amount, email);
					orderDispatcher.send("ECOMMERCE_NEW_ORDER", email, order);
					
					var emailCode = new Email("Email", "Thank you for order! We are processing your order!");
					emailDispatcher.send("ECOMMERCE_SEND_EMAIL", email, emailCode);
				}
			}
		}
	}
}
