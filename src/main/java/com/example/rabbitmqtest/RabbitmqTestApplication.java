package com.example.rabbitmqtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@ComponentScan(value = {"com.example.rabbitmqtest"}, excludeFilters = {@ComponentScan.Filter(
		type = FilterType.ASSIGNABLE_TYPE,
		value = {
				com.example.rabbitmqtest.MessagingConfiguration.class,
		})})
@SpringBootApplication
public class RabbitmqTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(RabbitmqTestApplication.class, args);
	}

}
