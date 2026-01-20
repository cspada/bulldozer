package com.bulldozer.interview.cspada.inventory.service.stock;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class StockRepositoryTest {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void shouldFindByType() {
        // Given
        StockEntity beerStock = new StockEntity();
        beerStock.setAlcohol(Alcohol.BEER);
        beerStock.setAvailableCount(10);
        entityManager.persist(beerStock);
        entityManager.flush();

        // When
        StockEntity found = stockRepository.findByAlcohol(Alcohol.BEER);

        // Then
        assertThat(found).isNotNull();
        assertThat(found.getAvailableCount()).isEqualTo(10);
    }

    @Test
    void shouldIncreaseAvailableCount() {
        // Given
        StockEntity beerStock = new StockEntity();
        beerStock.setAlcohol(Alcohol.BEER);
        beerStock.setAvailableCount(5);
        entityManager.persist(beerStock);
        entityManager.flush();

        // When
        stockRepository.increaseAvailableCount(Alcohol.BEER, 15);
        entityManager.clear(); // On vide le cache de l'EM pour forcer la lecture en base

        // Then
        StockEntity updated = stockRepository.findByAlcohol(Alcohol.BEER);
        assertThat(updated.getAvailableCount()).isEqualTo(20);
    }
}
