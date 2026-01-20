package com.bulldozer.interview.cspada.inventory.service;

import com.bulldozer.interview.cspada.inventory.service.stock.Alcohol;
import com.bulldozer.interview.cspada.inventory.service.stock.NoAvailableStockException;
import com.bulldozer.interview.cspada.inventory.service.stock.StockEntity;
import com.bulldozer.interview.cspada.inventory.service.stock.StockRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class InventoryControllerTest {

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private InventoryController inventoryController;

    @Test
    void failsWhenNoAvailableStock() {
        Mockito.doReturn(null).when(stockRepository).findByAlcohol(Alcohol.BEER);
        assertThrows(NoAvailableStockException.class, () -> inventoryController.inventory());
    }

    @Test
    void returnsTheAvailableStockFromRepository() {
        StockEntity beerStock = new StockEntity();
        beerStock.setAlcohol(Alcohol.BEER);
        beerStock.setAvailableCount(2);
        Mockito.doReturn(beerStock).when(stockRepository).findByAlcohol(Alcohol.BEER);

        int count = inventoryController.inventory();
        assertThat(count).isEqualTo(2);
    }

    @Test
    void refillWithExistingStockShouldNotSaveANewEntity() {
        Mockito.doReturn(1).when(stockRepository).increaseAvailableCount(Alcohol.BEER, 5);
        inventoryController.refill(5);

        verify(stockRepository, times(0)).save(any());
    }

    @Test
    void refillWithNonExistingStockShouldSaveANewEntity() {
        Mockito.doReturn(0).when(stockRepository).increaseAvailableCount(Alcohol.BEER, 5);
        inventoryController.refill(5);

        ArgumentCaptor<StockEntity> beerStockCaptor = ArgumentCaptor.forClass(StockEntity.class);
        verify(stockRepository).save(beerStockCaptor.capture());
        StockEntity beerStock = beerStockCaptor.getValue();
        assertThat(beerStock)
                .returns(Alcohol.BEER, StockEntity::getAlcohol)
                .returns(5, StockEntity::getAvailableCount);
    }
}