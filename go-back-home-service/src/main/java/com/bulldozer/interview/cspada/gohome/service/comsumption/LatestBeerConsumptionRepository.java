package com.bulldozer.interview.cspada.gohome.service.comsumption;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LatestBeerConsumptionRepository extends JpaRepository<LatestBeerConsumptionEntity, String> {

    @Transactional(readOnly = true)
    List<LatestBeerConsumptionEntity> findByConsumedAtAfter(LocalDateTime dateTime);
}
