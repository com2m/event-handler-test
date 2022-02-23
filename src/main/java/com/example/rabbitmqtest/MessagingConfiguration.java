package com.example.rabbitmqtest;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;

@Configuration
public class MessagingConfiguration {

	@Bean
	public ConnectionFactory connectionFactory() {
		return new CachingConnectionFactory("localhost");
	}

	@Bean
	public RetryOperationsInterceptor workMessagesRetryInterceptor() {
		return RetryInterceptorBuilder.stateless()
				.maxAttempts(1000)
				.backOffOptions(1000, 1.5, 600000)
				.build();
	}

	@Bean
	public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		factory.setPrefetchCount(250);
		factory.setConnectionFactory(connectionFactory);
		factory.setConcurrentConsumers(6);
		factory.setMaxConcurrentConsumers(10);
		factory.setAdviceChain(workMessagesRetryInterceptor());

		return factory;
	}

	@Bean(name = "ServiceQueue")
	public Queue serviceQueue() {
		return QueueBuilder.nonDurable("events.queue.test-service").build();
	}

	@Bean
	public Binding serviceBinding(@Qualifier("ServiceQueue") Queue queue, Exchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with("private.topic.test-service").noargs();
	}

	@Bean
	public Exchange exchange() {
		return ExchangeBuilder.topicExchange("events.exchange").durable(false).build();
	}

}
