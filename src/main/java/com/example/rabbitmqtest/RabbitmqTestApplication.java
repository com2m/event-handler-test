package com.example.rabbitmqtest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.event.EventListener;

@ComponentScan(value = {"com.example.rabbitmqtest"}, excludeFilters = {@ComponentScan.Filter(
		type = FilterType.ASSIGNABLE_TYPE,
		value = {
				com.example.rabbitmqtest.MessagingConfiguration.class,
		})})
@SpringBootApplication
public class RabbitmqTestApplication {

	@Autowired
	private RabbitTemplate template;

	public static void main(String[] args) {
		SpringApplication.run(RabbitmqTestApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() {
		String message = "{\"itemId\":\"123\", \"payload\":\""+ StringUtils.repeat("*", 2000000) + "\"}";
		this.template.convertAndSend("events.exchange", "private.topic.test-service", message);
	}

}
