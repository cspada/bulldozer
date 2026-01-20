package com.bulldozer.interview.cspada.messaging.api;

/**
 * Defines an interface for publishing various domain events.
 * Implementations of this interface are responsible for handling
 * the actual publishing logic to the appropriate messaging mechanisms.
 */
public interface EventPublisher {

    /**
     * Publishes an event indicating that a beer order has been placed.
     *
     * @param beerOrderedEvent the event containing details of the beer order,
     *                         such as the user who placed the order and the time of ordering
     */
    void publishBeerOrderedEvent(BeerOrderedEvent beerOrderedEvent);

    /**
     * Publishes an event indicating that there is a low stock condition.
     *
     * @param lowStockEvent the event containing details about the low stock condition
     */
    void publishLowStockEvent(LowStockEvent lowStockEvent);

    /**
     * Publishes an event indicating that a user should navigate back home.
     *
     * @param goBackHomeEvent the event containing details such as the user
     *                        who initiated the action to go back home
     */
    void publishGoBackHomeEvent(GoBackHomeEvent goBackHomeEvent);
}
