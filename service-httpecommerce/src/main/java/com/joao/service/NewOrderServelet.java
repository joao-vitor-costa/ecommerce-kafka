package com.joao.service;

import com.joao.dto.Email;
import com.joao.dto.Order;
import com.joao.produce.KafkaDispatcher;
import lombok.SneakyThrows;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderServelet extends HttpServlet {
	
	private final KafkaDispatcher<Order> orderDispatcher = new KafkaDispatcher<>();
	private final KafkaDispatcher<Email> emailDispatcher = new KafkaDispatcher<>();
	
	@SneakyThrows
	@Override
	public void destroy() {
		super.destroy();
		orderDispatcher.close();
		emailDispatcher.close();
		
	}
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		try {
			//localhost:8080/new?email=jjTa@email.com&amount=157
			//we are not caring about anything security issues here, we are only
			// showing how to use https as a starting point
			var email = req.getParameter("email");
			var amount = new BigDecimal(req.getParameter("amount"));
			
			var orderId = UUID.randomUUID().toString();
			var order = new Order(orderId, amount, email);
			orderDispatcher.send("ECOMMERCE_NEW_ORDER", email, order);
			
			var emailCode = new Email("Email", "Thank you for order! We are processing your order!");
			emailDispatcher.send("ECOMMERCE_SEND_EMAIL", email, emailCode);
			
			System.out.println("new order sent successfully");
			resp.setStatus(HttpServletResponse.SC_OK);
			resp.getWriter().println("new order sent");
		} catch (ExecutionException e) {
			throw new ServletException(e);
		} catch (InterruptedException e) {
			throw new ServletException(e);
		}
	}
	
}
