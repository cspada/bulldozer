package com.bulldozer.interview.cspada.bar.service.comsumption;

import com.bulldozer.interview.cspada.bar.service.inventory.InventoryClient;
import com.bulldozer.interview.cspada.messaging.api.BeerOrderedEvent;
import com.bulldozer.interview.cspada.messaging.api.EventPublisher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class BeerConsumptionServiceTest {

    @Spy
    private Clock clock = Clock.fixed(Instant.parse("2026-01-20T18:00:00Z"), ZoneId.systemDefault());

    @Mock
    private BeerConsumptionRepository beerConsumptionRepository;

    @Mock
    private InventoryClient inventoryClient;

    @Mock
    private EventPublisher eventPublisher;

    @InjectMocks
    private BeerConsumptionService beerConsumptionService;

    @Test
    void consumeBeerWithoutStockThrowException() {
        Mockito.doReturn(0).when(inventoryClient).getInventory();
        assertThatThrownBy(() -> beerConsumptionService.consumeBeer("user"))
                .isInstanceOf(StockIsEmptyException.class);
    }

    @Test
    void consumeBeerWithStock() {
        LocalDateTime now = LocalDateTime.now(clock);
        Mockito.doReturn(10).when(inventoryClient).getInventory();

        beerConsumptionService.consumeBeer("user");

        ArgumentCaptor<BeerConsumptionEntity> argumentCaptor = ArgumentCaptor.forClass(BeerConsumptionEntity.class);
        Mockito.verify(beerConsumptionRepository).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue())
                .returns("user", BeerConsumptionEntity::getUserId)
                .returns(now, BeerConsumptionEntity::getConsumedAt);

        BeerOrderedEvent expectedEvent = new BeerOrderedEvent("user", now);
        Mockito.verify(eventPublisher).publishBeerOrderedEvent(expectedEvent);
    }
}