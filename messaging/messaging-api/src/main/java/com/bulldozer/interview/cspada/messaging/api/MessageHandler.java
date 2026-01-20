package com.bulldozer.interview.cspada.messaging.api;

/**
 * Defines a handler interface for processing different types of events related to the messaging system.
 * Implementations of this interface should provide the logic for handling each specific event.
 */
public interface MessageHandler {

    /**
     * Handles the event triggered when a beer order is placed.
     *
     * @param event the event containing details of the beer order,
     *              including the user who initiated the order and the timestamp of the order
     */
    void handleBeerOrdered(BeerOrderedEvent event);

    /**
     * Handles the event triggered when a user is instructed to navigate back to home.
     *
     * @param event the event containing details such as the user ID of the individual
     *              who initiated the go-back-home action
     */
    void handleGoBackHome(GoBackHomeEvent event);

    /**
     * Handles the event triggered when there is a low stock condition.
     *
     * @param event the event containing details about the current stock level
     */
    void handleLowStockEvent(LowStockEvent event);
}
