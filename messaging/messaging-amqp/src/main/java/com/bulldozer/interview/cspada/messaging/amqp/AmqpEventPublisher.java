package com.bulldozer.interview.cspada.messaging.amqp;

import com.bulldozer.interview.cspada.messaging.api.BeerOrderedEvent;
import com.bulldozer.interview.cspada.messaging.api.EventPublisher;
import com.bulldozer.interview.cspada.messaging.api.GoBackHomeEvent;
import com.bulldozer.interview.cspada.messaging.api.LowStockEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AmqpEventPublisher implements EventPublisher {

    public static final String TYPE_ID_HEADER = "__TypeId__";

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publishBeerOrderedEvent(BeerOrderedEvent beerOrderedEvent) {
        rabbitTemplate.convertAndSend(AmqpSchema.EXCHANGE_NAME,
                null, beerOrderedEvent,
                addTypeIdHeader(TypeHeaders.BEER_ORDERED));
    }

    @Override
    public void publishLowStockEvent(LowStockEvent lowStockEvent) {
        rabbitTemplate.convertAndSend(AmqpSchema.EXCHANGE_NAME,
                null, lowStockEvent,
                addTypeIdHeader(TypeHeaders.LOW_STOCK));
    }

    @Override
    public void publishGoBackHomeEvent(GoBackHomeEvent goBackHomeEvent) {
        rabbitTemplate.convertAndSend(AmqpSchema.EXCHANGE_NAME,
                null, goBackHomeEvent,
                addTypeIdHeader(TypeHeaders.GO_BACK_HOME));
    }

    private MessagePostProcessor addTypeIdHeader(String type) {
        return message -> {
            message.getMessageProperties().setHeader(TYPE_ID_HEADER, type);
            return message;
        };
    }
}
