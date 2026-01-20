package com.bulldozer.interview.cspada.gohome.service;

import com.bulldozer.interview.cspada.gohome.service.comsumption.ConsumptionService;
import com.bulldozer.interview.cspada.messaging.api.BeerOrderedEvent;
import com.bulldozer.interview.cspada.messaging.api.GoBackHomeEvent;
import com.bulldozer.interview.cspada.messaging.api.LowStockEvent;
import com.bulldozer.interview.cspada.messaging.api.MessageHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GoBackHomeMessageHandler implements MessageHandler {

    private final ConsumptionService consumptionService;

    @Override
    public void handleBeerOrdered(BeerOrderedEvent event) {
        consumptionService.consumeBeer(event.userId(), event.orderedAt());
    }

    @Override
    public void handleGoBackHome(GoBackHomeEvent event) {
        // Ignored by this service
    }

    @Override
    public void handleLowStockEvent(LowStockEvent event) {
        // Ignored by this service
    }
}
