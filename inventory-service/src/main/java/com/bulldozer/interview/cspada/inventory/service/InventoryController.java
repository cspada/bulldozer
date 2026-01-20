package com.bulldozer.interview.cspada.inventory.service;

import com.bulldozer.interview.cspada.inventory.service.stock.Alcohol;
import com.bulldozer.interview.cspada.inventory.service.stock.NoAvailableStockException;
import com.bulldozer.interview.cspada.inventory.service.stock.StockEntity;
import com.bulldozer.interview.cspada.inventory.service.stock.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Transactional
@RestController
@RequiredArgsConstructor
public class InventoryController {

    private final StockRepository stockRepository;

    /**
     * Retrieves the current available stock count for beer.
     * This method checks the inventory for the BEER type and returns
     * the available count. If no stock is found for BEER, a
     * {@link NoAvailableStockException} is thrown.
     *
     * @return the available stock count for beer
     * @throws NoAvailableStockException if no stock is initialized for beer
     */
    @GetMapping("/inventory")
    @PreAuthorize("hasAuthority('ROLE_EMPLOYEE')")
    public int inventory() {
        StockEntity beerStock = stockRepository.findByAlcohol(Alcohol.BEER);
        if (beerStock == null) {
            throw new NoAvailableStockException(Alcohol.BEER);
        }
        return beerStock.getAvailableCount();
    }

    @PostMapping("/inventory/refill")
    @PreAuthorize("hasRole('ADMIN')")
    public void refill(@RequestParam("count") int count) {
        int updated = stockRepository.increaseAvailableCount(Alcohol.BEER, count);
        if (updated != 1) {
            StockEntity beerStock = new StockEntity();
            beerStock.setAlcohol(Alcohol.BEER);
            beerStock.setAvailableCount(count);
            stockRepository.save(beerStock);
        }
    }
}
