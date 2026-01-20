package com.bulldozer.interview.cspada.messaging.amqp;

import com.bulldozer.interview.cspada.messaging.api.BeerOrderedEvent;
import com.bulldozer.interview.cspada.messaging.api.GoBackHomeEvent;
import com.bulldozer.interview.cspada.messaging.api.LowStockEvent;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Configures the messaging components for AMQP-based message publishing and consumption.
 * This class sets up the necessary infrastructure for publishing and receiving events
 * using RabbitMQ, including the topic exchange, queue, bindings, and message conversion.
 */
@Configuration
@EnableRabbit
@ComponentScan(basePackageClasses = AmqpMessagingAutoConfiguration.class)
public class AmqpMessagingAutoConfiguration {

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(AmqpSchema.EXCHANGE_NAME);
    }

    @Bean
    public Queue queue() {
        return QueueBuilder.durable(AmqpSchema.EVENT_STREAM)
                .stream()
                .build();
    }

    @Bean
    public Binding bindingBeerOrdered(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("");
    }

    @Bean
    public JacksonJsonMessageConverter messageConverter() {
        JacksonJsonMessageConverter messageConverter = new JacksonJsonMessageConverter();
        DefaultClassMapper classMapper = new DefaultClassMapper();
        classMapper.setTrustedPackages("*");
        Map<String, Class<?>> idClassMapping = new HashMap<>();
        idClassMapping.put(TypeHeaders.BEER_ORDERED, BeerOrderedEvent.class);
        idClassMapping.put(TypeHeaders.LOW_STOCK, LowStockEvent.class);
        idClassMapping.put(TypeHeaders.GO_BACK_HOME, GoBackHomeEvent.class);
        classMapper.setIdClassMapping(idClassMapping);
        messageConverter.setClassMapper(classMapper);
        return messageConverter;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         JacksonJsonMessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }
}
