package com.bulldozer.interview.cspada.messaging.amqp;

import com.bulldozer.interview.cspada.messaging.api.BeerOrderedEvent;
import com.bulldozer.interview.cspada.messaging.api.GoBackHomeEvent;
import com.bulldozer.interview.cspada.messaging.api.LowStockEvent;
import com.bulldozer.interview.cspada.messaging.api.MessageHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RabbitListener(queues = AmqpSchema.EVENT_STREAM)
@ConditionalOnProperty(name = "bulldozer.messaging.listener.enabled", havingValue = "true")
public class MessagingListener {

    private final MessageHandler messageHandler;

    public MessagingListener(MessageHandler messageHandler) {
        log.info("Messaging listener is enabled.");
        this.messageHandler = messageHandler;
    }

    @RabbitHandler
    public void handleBeerOrdered(BeerOrderedEvent event) {
        log.debug("Received beer ordered event for user {}", event.userId());
        messageHandler.handleBeerOrdered(event);
    }

    @RabbitHandler
    public void handleGoBackHome(GoBackHomeEvent event) {
        log.debug("Received go back home event for user {}", event.userId());
        messageHandler.handleGoBackHome(event);
    }

    @RabbitHandler
    public void handleLowStockEvent(LowStockEvent event) {
        log.debug("Received low stock event");
        messageHandler.handleLowStockEvent(event);
    }
}