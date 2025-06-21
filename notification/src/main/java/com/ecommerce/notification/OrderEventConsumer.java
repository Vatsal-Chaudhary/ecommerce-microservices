package com.ecommerce.notification;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;


@Service
@Slf4j
public class OrderEventConsumer {

//    @RabbitListener(queues = "${rabbitmq.queue.name}")
//    public void handleOrderEvent(OrderCreatedEvent event) {
//        System.out.println("Received order event: " + event);
//
//        long orderId = event.getOrderId();
//        OrderStatus status = event.getStatus();
//
//        System.out.println("Processing order ID: " + orderId + " with status: " + status);
//    }

    @Bean
    public Consumer<OrderCreatedEvent> orderCreated() {
        return event -> {
            log.info("Received order created event for order: {}", event.getOrderId());
            log.info("Received order created event for user: {}", event.getUserId());
        };
    }
}
