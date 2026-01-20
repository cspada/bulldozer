package com.bulldozer.interview.cspada.gohome.service.comsumption;

import com.bulldozer.interview.cspada.gohome.service.ApplicationProperties;
import com.bulldozer.interview.cspada.messaging.api.EventPublisher;
import com.bulldozer.interview.cspada.messaging.api.GoBackHomeEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsumptionService {

    private final Clock clock;

    private final LatestBeerConsumptionRepository latestBeerConsumptionRepository;

    private final EventPublisher eventPublisher;

    private final ApplicationProperties applicationProperties;

    @Transactional
    public void consumeBeer(String userId, LocalDateTime consumedAt) {
        log.info("Updating latest consumption beer for user {} at {}", userId, consumedAt);
        latestBeerConsumptionRepository.save(new LatestBeerConsumptionEntity(userId, consumedAt));
    }

    /**
     * Notifies users to go back home if their beer consumption entries meet specific criteria.
     *
     * This method calculates a time range based on the current time, a predefined notification trigger time,
     * and a consumption period defined in the application properties. It retrieves all beer consumption entries
     * that occurred after the calculated time range from the database and triggers a "go back home" event for each entry.
     *
     * The following operations are performed in this method:
     * 1. Computes the 'from' timestamp by combining the current time, the notification trigger time,
     *    and subtracting the last consumption period as defined in the application properties.
     * 2. Retrieves all beer consumption records from the repository that have a consumption time
     *    after the calculated 'from' timestamp.
     * 3. For each retrieved beer consumption record, a "go back home" event is published
     *    using the event publisher.
     *
     * This method operates within a transactional context to ensure consistency when interacting
     * with the underlying database.
     */
    public void notifyBackHome() {
        LocalDateTime from = LocalDateTime.now(clock)
                .with(applicationProperties.getNotificationTriggerTime())
                .minus(applicationProperties.getLastConsumptionPeriod());
        List<LatestBeerConsumptionEntity> consumptionsAfter = latestBeerConsumptionRepository.findByConsumedAtAfter(from);
        consumptionsAfter.forEach(this::publishGoBackHome);
    }

    protected void publishGoBackHome(LatestBeerConsumptionEntity entity) {
        log.info("Publishing go back home event for user {}", entity.getUserId());
        GoBackHomeEvent event = new GoBackHomeEvent(entity.getUserId());
        eventPublisher.publishGoBackHomeEvent(event);
    }
}
