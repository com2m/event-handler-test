package com.example.rabbitmqtest;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;


@Service
public class EventHandlerService {

	private final RabbitTemplate template;

	public EventHandlerService(RabbitTemplate template) {
		this.template = template;
	}

	@RabbitListener(queues = {"events.queue.test-service"})
	public void handleEvents(String data) {
		template.convertAndSend("events.exchange", "components/request/123", data);
	}
}
