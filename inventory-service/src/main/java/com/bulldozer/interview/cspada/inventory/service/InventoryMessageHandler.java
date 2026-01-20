package com.bulldozer.interview.cspada.inventory.service;

import com.bulldozer.interview.cspada.inventory.service.stock.Alcohol;
import com.bulldozer.interview.cspada.inventory.service.stock.StockService;
import com.bulldozer.interview.cspada.messaging.api.BeerOrderedEvent;
import com.bulldozer.interview.cspada.messaging.api.GoBackHomeEvent;
import com.bulldozer.interview.cspada.messaging.api.LowStockEvent;
import com.bulldozer.interview.cspada.messaging.api.MessageHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class InventoryMessageHandler implements MessageHandler {

    private final StockService stockService;

    @Override
    public void handleBeerOrdered(BeerOrderedEvent event) {
        stockService.decreaseStock(Alcohol.BEER);
    }

    @Override
    public void handleGoBackHome(GoBackHomeEvent event) {
        // Ignored by this service
    }

    @Override
    public void handleLowStockEvent(LowStockEvent event) {
        // Ignored by this service, only published.
    }
}