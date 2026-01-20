package com.bulldozer.interview.cspada.bar.service.comsumption;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BeerConsumptionRepository extends JpaRepository<BeerConsumptionEntity, Long> {
}
