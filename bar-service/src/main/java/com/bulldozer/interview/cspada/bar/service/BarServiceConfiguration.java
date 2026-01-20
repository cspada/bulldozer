package com.bulldozer.interview.cspada.bar.service;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
@EnableConfigurationProperties(ApplicationProperties.class)
public class BarServiceConfiguration {

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }
}
