package com.bulldozer.interview.cspada.inventory.service;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "bulldozer.inventory")
public class ApplicationProperties {

    private int lowStockTrigger;
}
