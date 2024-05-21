package com.manideep.order.event;

public class OrderPlacedEvent {
    private String orderId;

    public OrderPlacedEvent() {
    }
    public OrderPlacedEvent(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
