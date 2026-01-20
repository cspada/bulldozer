package com.bulldozer.interview.cspada.messaging.amqp;

import lombok.experimental.UtilityClass;

/**
 * Utility class containing constant string values used as type headers for message classification
 * in the messaging system. These headers are typically mapped to specific event types to enable
 * deserialization and processing by the message consumer.
 */
@UtilityClass
public class TypeHeaders {

    public static final String BEER_ORDERED = "beerOrderedType";
    public static final String GO_BACK_HOME = "goBackHomeType";
    public static final String LOW_STOCK = "lowStockType";
}
