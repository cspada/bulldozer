package com.bulldozer.interview.cspada.inventory.service.stock;

import com.bulldozer.interview.cspada.inventory.service.ApplicationProperties;
import com.bulldozer.interview.cspada.messaging.api.EventPublisher;
import com.bulldozer.interview.cspada.messaging.api.LowStockEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;

    private final ApplicationProperties applicationProperties;

    private final EventPublisher eventPublisher;

    @Transactional
    public void decreaseStock(Alcohol alcohol) {
        log.info("Decreasing stock by 1 for {}", alcohol);
        stockRepository.decrease(alcohol);
        StockEntity currentStock = stockRepository.findByAlcohol(alcohol);
        if (currentStock.getAvailableCount() == applicationProperties.getLowStockTrigger()) {
            publishLowStockEvent(currentStock);
        }
    }

    private void publishLowStockEvent(StockEntity currentStock) {
        log.info("Publishing low stock event for {}", currentStock.getAlcohol());
        LowStockEvent event = new LowStockEvent(currentStock.getAlcohol().name(), currentStock.getAvailableCount());
        eventPublisher.publishLowStockEvent(event);
    }
}
