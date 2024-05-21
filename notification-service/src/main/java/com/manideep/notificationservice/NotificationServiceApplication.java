package com.manideep.notificationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
public class NotificationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }


    @KafkaListener(topics = "notificationTopic")
    public void notificationListner(OrderPlacedEvent orderPlacedEvent){
        System.out.println("Order placed - Email Sent - OrderId:" + orderPlacedEvent.getOrderId());
    }
}
