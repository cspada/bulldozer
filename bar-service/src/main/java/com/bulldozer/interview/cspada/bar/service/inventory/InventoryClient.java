package com.bulldozer.interview.cspada.bar.service.inventory;

import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange("/inventory")
public interface InventoryClient {

    @GetExchange
    int getInventory();
}
