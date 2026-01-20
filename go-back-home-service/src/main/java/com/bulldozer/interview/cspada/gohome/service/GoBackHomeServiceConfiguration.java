package com.bulldozer.interview.cspada.gohome.service;

import com.bulldozer.interview.cspada.gohome.service.comsumption.ConsumptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.support.CronTrigger;

import java.time.Clock;
import java.time.LocalTime;
import java.util.concurrent.ScheduledFuture;

@Slf4j
@Configuration
@EnableScheduling
@EnableConfigurationProperties(ApplicationProperties.class)
public class GoBackHomeServiceConfiguration {

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }

    /**
     * Schedules a task that triggers the notification for users to go back home.
     * The task runs based on a cron expression derived from the notification trigger time
     * specified in the application properties.
     */
    @Bean
    public ScheduledFuture<?> scheduledGoBackHomeTask(TaskScheduler taskScheduler,
                                                      ConsumptionService consumptionService,
                                                      ApplicationProperties properties) {
        LocalTime triggerTime = properties.getNotificationTriggerTime();
        String cronExpression = "0 %d %d * * *".formatted(triggerTime.getMinute(), triggerTime.getHour());

        log.info("Configuring GoBackHome scheduled task using cron expression {}", cronExpression);

        return taskScheduler.schedule(() -> {
            log.info("Running GoBackHome scheduled task...");
            consumptionService.notifyBackHome();
        }, new CronTrigger(cronExpression));
    }
}
