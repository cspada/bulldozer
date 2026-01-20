package com.bulldozer.interview.cspada.bar.service.comsumption;

import com.bulldozer.interview.cspada.bar.service.inventory.InventoryClient;
import com.bulldozer.interview.cspada.messaging.api.BeerOrderedEvent;
import com.bulldozer.interview.cspada.messaging.api.EventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class BeerConsumptionService {

    private final Clock clock;

    private final BeerConsumptionRepository beerConsumptionRepository;

    private final InventoryClient inventoryClient;

    private final EventPublisher eventPublisher;

    @Transactional
    public void consumeBeer(String userId) {
        LocalDateTime now = LocalDateTime.now(clock);

        int currentStock = inventoryClient.getInventory();
        log.info("Current stock is {}", currentStock);
        if (currentStock <= 0) {
            throw new StockIsEmptyException();
        }

        log.info("Create and save a new beer consumption for user {}", userId);
        beerConsumptionRepository.save(new BeerConsumptionEntity(null, userId, now));

        log.info("Publishing beer ordered event for user {}", userId);
        eventPublisher.publishBeerOrderedEvent(new BeerOrderedEvent(userId, now));
    }
}
