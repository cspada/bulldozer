package com.bulldozer.interview.cspada.gohome.service;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.time.LocalTime;

@Data
@ConfigurationProperties(prefix = "bulldozer.go-home", ignoreInvalidFields = true)
public class ApplicationProperties {

    private LocalTime notificationTriggerTime;

    private Duration lastConsumptionPeriod;
}
