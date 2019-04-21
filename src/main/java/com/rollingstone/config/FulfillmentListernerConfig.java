package com.rollingstone.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

@Configuration
public class FulfillmentListernerConfig {

	@Value("${fulfillment.exchange.name}")
	private String fulfillmentExchangeName;
	
	@Value("${fulfillment.queue.name}")
	private String fulfillmentQueueName;
	
	@Value("${fulfillment.routing.name.key}")
	private String fulfillmentRoutingKeyName;
	
	@Bean
	public FanoutExchange getAccountSalesOrderFanoutExchange() {
		return new FanoutExchange(fulfillmentExchangeName);
	}
	
	@Bean
	public Queue getFulfillmentQueue() {
		return new Queue(fulfillmentQueueName);
	}
	
	@Bean
	public Binding bindAccountQueueForExchange() {
		return BindingBuilder.bind(getFulfillmentQueue()).to(getAccountSalesOrderFanoutExchange());
	}
	
	@Bean
	public Jackson2JsonMessageConverter producerJackson2JsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}
	
	@Bean
	public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
		return new MappingJackson2MessageConverter();
	}
	@Bean 
	public DefaultMessageHandlerMethodFactory messageHandlerMethodFacotry() {
		DefaultMessageHandlerMethodFactory factory  = new DefaultMessageHandlerMethodFactory();
		factory.setMessageConverter(consumerJackson2MessageConverter());
		return factory;
	}
	
	public void configureRabbitListeners(final RabbitListenerEndpointRegistrar registrar) {
		registrar.setMessageHandlerMethodFactory(messageHandlerMethodFacotry());
	}
}
