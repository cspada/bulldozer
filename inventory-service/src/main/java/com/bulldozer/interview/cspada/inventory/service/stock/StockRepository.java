package com.bulldozer.interview.cspada.inventory.service.stock;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface StockRepository extends JpaRepository<StockEntity, Alcohol> {

    @Transactional(readOnly = true)
    StockEntity findByAlcohol(Alcohol alcohol);

    @Modifying
    @Query("UPDATE StockEntity s SET s.availableCount = s.availableCount + :count WHERE s.alcohol = :alcohol")
    int increaseAvailableCount(@Param("alcohol") Alcohol alcohol, @Param("count") int count);

    @Modifying
    @Query("UPDATE StockEntity s SET s.availableCount = s.availableCount - 1 WHERE s.alcohol = :alcohol")
    int decrease(@Param("alcohol") Alcohol alcohol);
}
