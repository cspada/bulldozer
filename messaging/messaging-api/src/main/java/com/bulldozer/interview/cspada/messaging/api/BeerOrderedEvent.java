package com.bulldozer.interview.cspada.messaging.api;

import java.time.LocalDateTime;

public record BeerOrderedEvent(String userId, LocalDateTime orderedAt) {
}
