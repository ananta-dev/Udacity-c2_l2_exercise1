package com.udacity.jdnd.course3.lesson2;

import com.udacity.jdnd.course3.lesson2.entity.Order;
import com.udacity.jdnd.course3.lesson2.entity.OrderItem;
import com.udacity.jdnd.course3.lesson2.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@SpringBootApplication
@EnableJpaRepositories
public class Lesson2Application {

	private static final Logger log = LoggerFactory.getLogger(Lesson2Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Lesson2Application.class, args);
	}

	@Bean
	public CommandLineRunner demo(OrderRepository repository) {
		return (args) -> {
			// save a couple of customers
			Order order = new Order();
			order.setCustomerName("Mary Doolittle");
			order.setCustomerAddress("123 Main Street, Birmingham, AL, 40861, USA");
			order.setCreatedTime(Timestamp.valueOf(LocalDateTime.now()));

			// create order item1
			OrderItem orderItem1 = new OrderItem();
			orderItem1.setOrder(order);
			orderItem1.setItemName("Parachute");
			orderItem1.setItemCount(3);


			// create order item2
			OrderItem orderItem2 = new OrderItem();
			orderItem2.setOrder(order);
			orderItem2.setItemName("Hand glider");
			orderItem2.setItemCount(3);

			order = repository.save(order);
			System.err.println("Saved Order ID: " + order.getOrderId());

			Optional<Order> orderRead = repository.findByCustomerName("Mary Doolittle");
			orderRead.ifPresent(value ->System.err.println("Found by Customer Name - Order: " + value));

		};
	}
}
