package com.bulldozer.interview.cspada.inventory.service.stock;

import com.bulldozer.interview.cspada.inventory.service.ApplicationProperties;
import com.bulldozer.interview.cspada.messaging.api.EventPublisher;
import com.bulldozer.interview.cspada.messaging.api.LowStockEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StockServiceTest {

    @Mock
    private StockRepository stockRepository;

    @Mock
    private ApplicationProperties applicationProperties;

    @Mock
    private EventPublisher eventPublisher;

    @InjectMocks
    private StockService stockService;

    @Test
    void lowStockEventIsPublishedWhenStockIsLow() {
        Mockito.doReturn(5).when(applicationProperties).getLowStockTrigger();
        StockEntity currentStock = new StockEntity(Alcohol.BEER, 5);
        Mockito.doReturn(currentStock).when(stockRepository).findByAlcohol(Alcohol.BEER);

        stockService.decreaseStock(Alcohol.BEER);

        Mockito.verify(stockRepository).decrease(Alcohol.BEER);

        LowStockEvent expectedEvent = new LowStockEvent(Alcohol.BEER.name(), 5);
        Mockito.verify(eventPublisher).publishLowStockEvent(expectedEvent);
    }

    @Test
    void lowStockEventIsNotPublishedWhenStockIsFine() {
        Mockito.doReturn(5).when(applicationProperties).getLowStockTrigger();
        StockEntity currentStock = new StockEntity(Alcohol.BEER, 10);
        Mockito.doReturn(currentStock).when(stockRepository).findByAlcohol(Alcohol.BEER);

        stockService.decreaseStock(Alcohol.BEER);

        Mockito.verify(stockRepository).decrease(Alcohol.BEER);
        Mockito.verifyNoInteractions(eventPublisher);
    }

}