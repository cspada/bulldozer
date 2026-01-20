package com.bulldozer.interview.cspada.inventory.service.stock;

/**
 * Exception thrown when no stock is available for a specific type of alcohol.
 * This exception indicates that the requested {@link Alcohol} does not have
 * any stock initialized in the inventory system.
 */
public class NoAvailableStockException extends RuntimeException {

    public NoAvailableStockException(Alcohol alcohol) {
        super("No stock initialized for type %s".formatted(alcohol));
    }
}
