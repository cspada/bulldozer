package com.bulldozer.interview.cspada.gohome.service.comsumption;

import com.bulldozer.interview.cspada.gohome.service.ApplicationProperties;
import com.bulldozer.interview.cspada.messaging.api.EventPublisher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ConsumptionServiceTest {

    @Spy
    private Clock clock = Clock.fixed(Instant.parse("2026-01-20T18:00:00Z"), ZoneId.systemDefault());

    @Mock
    private LatestBeerConsumptionRepository latestBeerConsumptionRepository;

    @Mock
    private EventPublisher eventPublisher;

    @Mock
    private ApplicationProperties applicationProperties;

    @InjectMocks
    private ConsumptionService consumptionService;

    @Test
    void consumeBeerUpdatesTheLatestConsumeDate() {
        LocalDateTime consumedAt = LocalDateTime.parse("2026-01-20T18:00:00");

        consumptionService.consumeBeer("user", consumedAt);

        ArgumentCaptor<LatestBeerConsumptionEntity> argumentCaptor = ArgumentCaptor.forClass(LatestBeerConsumptionEntity.class);
        Mockito.verify(latestBeerConsumptionRepository).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue())
                .returns("user", LatestBeerConsumptionEntity::getUserId)
                .returns(consumedAt, LatestBeerConsumptionEntity::getConsumedAt);
    }

    @Test
    void notifyBackHomeWithoutConsumptionDoNotPublishEvent() {
        LocalTime triggerTime = LocalTime.parse("02:00");
        // From = run day at 02:00 minus 6 hours
        LocalDateTime from = LocalDateTime.parse("2026-01-19T20:00");
        Mockito.doReturn(triggerTime).when(applicationProperties).getNotificationTriggerTime();
        Mockito.doReturn(Duration.of(6, ChronoUnit.HOURS)).when(applicationProperties).getLastConsumptionPeriod();
        Mockito.doReturn(List.of()).when(latestBeerConsumptionRepository).findByConsumedAtAfter(from);

        consumptionService.notifyBackHome();

        Mockito.verifyNoInteractions(eventPublisher);
    }
}
