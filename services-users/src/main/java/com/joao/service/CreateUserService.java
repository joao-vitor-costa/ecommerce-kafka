package com.joao.service;

import com.joao.dto.Order;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class CreateUserService {
	
	
	private final Connection conncetion;
	
	public CreateUserService() throws SQLException {
		String url = "jdbc:sqlite:users_database.db";
		this.conncetion = DriverManager.getConnection(url);
		try {
			conncetion.createStatement().execute("create table Users(" +
					"uuid varchar(200) primary key," +
					"email varchar (200))");
		} catch (SQLException exception) {
			// be careful, there sql could be wrong sql, be really careful
			exception.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws SQLException {
		var createUserService = new CreateUserService();
		try (var service = new KafkaService(CreateUserService.class.getSimpleName(), "ECOMMERCE_NEW_ORDER",
				createUserService::parse, Order.class, Map.of())) {
			service.run();
		}
	}
	
	private void parse(ConsumerRecord<String, Order> record) throws ExecutionException, InterruptedException,
			SQLException {
		System.out.println("-------------------------------------");
		System.out.println("Processing new order, checking for new user");
		System.out.println(record.value());
		var order = record.value();
		if (isNewUser(order.getEmail())) {
			insertNewUser(order.getUserId(), order.getEmail());
		}
		
	}
	
	private void insertNewUser(String uuid, String email) throws SQLException {
		var insertUser = conncetion.prepareStatement("insert Users (uuid, email)" +
				"values (?,?)");
		insertUser.setString(1, uuid);
		insertUser.setString(2, email);
		insertUser.execute();
		System.out.println("User uuid e " + email + "auditioned");
		
	}
	
	private boolean isNewUser(String email) throws SQLException {
		var exists = conncetion.prepareStatement("select uuid from Users where email = ? limit 1");
		exists.setString(1, email);
		var results = exists.executeQuery();
		return !results.next();
	}
}
