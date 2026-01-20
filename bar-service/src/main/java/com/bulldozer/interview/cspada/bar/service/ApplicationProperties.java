package com.bulldozer.interview.cspada.bar.service;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "bulldozer")
public class ApplicationProperties {

    private Inventory inventory;

    @Data
    public static class Inventory {

        private String baseUrl;
    }
}
