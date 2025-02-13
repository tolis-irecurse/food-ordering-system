package com.nttdata.food.ordering.system.order.service.messaging.mapper;

import com.nttdata.food.ordering.system.kafka.order.avro.model.PaymentOrderStatus;
import com.nttdata.food.ordering.system.kafka.order.avro.model.PaymentRequestAvroModel;
import com.nttdata.food.ordering.system.service.domain.event.OrderCreatedEvent;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OrderMessagingDataMapper {

    public PaymentRequestAvroModel mapOrderCreatedEventToPaymentRequestAvroModel(OrderCreatedEvent orderCreatedEvent) {

        var order = orderCreatedEvent.getOrder();

        return PaymentRequestAvroModel.newBuilder()
                .setId(UUID.randomUUID()) //TODO: Check this
                .setSagaId(UUID.randomUUID()) //TODO: Check this
                .setCustomerId(order.getCustomerId().getValue())
                .setOrderId(order.getId().getValue())
                .setPrice(order.getPrice().getAmount())
                .setCreatedAt(orderCreatedEvent.getCreatedAt().toInstant())
                .setPaymentOrderStatus(PaymentOrderStatus.PENDING)
                .build();
    }

    public PaymentRequestAvroModel mapOrderCancelledEventToPaymentRequestAvroModel(OrderCreatedEvent orderCreatedEvent) {

        var order = orderCreatedEvent.getOrder();

        return PaymentRequestAvroModel.newBuilder()
                .setId(UUID.randomUUID())
                .setSagaId(UUID.randomUUID())
                .setCustomerId(order.getCustomerId().getValue())
                .setOrderId(order.getId().getValue())
                .setPrice(order.getPrice().getAmount())
                .setCreatedAt(orderCreatedEvent.getCreatedAt().toInstant())
                .setPaymentOrderStatus(PaymentOrderStatus.CANCELLED)
                .build();

    }
}
