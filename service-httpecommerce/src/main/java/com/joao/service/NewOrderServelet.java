package com.joao.service;

import com.joao.dto.Email;
import com.joao.dto.Order;
import com.joao.produce.KafkaDispatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderServelet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try (var orderDispatcher = new KafkaDispatcher<Order>()) {
			try (var emailDispatcher = new KafkaDispatcher<Email>()) {
				try {
					var orderId = UUID.randomUUID().toString();
					var amount = new BigDecimal(Math.random() * 5000 + 1);
					var email = Math.random() + "@email.com";
					var order = new Order(orderId, amount, email);
					orderDispatcher.send("ECOMMERCE_NEW_ORDER", email, order);
					
					var emailCode = new Email("Email", "Thank you for order! We are processing your order!");
					emailDispatcher.send("ECOMMERCE_SEND_EMAIL", email, emailCode);
				} catch (ExecutionException e) {
					throw  new ServletException(e);
				} catch (InterruptedException e) {
					throw  new ServletException(e);
				}
			}
			
		}
	}
}
