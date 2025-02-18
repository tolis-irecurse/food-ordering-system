package com.nttdata.food.ordering.system.order.service.messaging.mapper;

import com.nttdata.food.ordering.system.common.domain.valueobject.OrderApprovalStatus;
import com.nttdata.food.ordering.system.common.domain.valueobject.PaymentStatus;
import com.nttdata.food.ordering.system.kafka.order.avro.model.*;
import com.nttdata.food.ordering.system.order.service.domain.dto.message.PaymentResponseMsg;
import com.nttdata.food.ordering.system.order.service.domain.dto.message.RestaurantApprovalResponseMsg;
import com.nttdata.food.ordering.system.service.domain.event.OrderCancelledEvent;
import com.nttdata.food.ordering.system.service.domain.event.OrderCreatedEvent;
import com.nttdata.food.ordering.system.service.domain.event.OrderPaidEvent;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderMessagingDataMapper {

    public PaymentRequestAvroModel mapOrderCreatedEventToPaymentRequestAvroModel(OrderCreatedEvent orderCreatedEvent) {

        var order = orderCreatedEvent.getOrder();

        return PaymentRequestAvroModel.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setSagaId(UUID.randomUUID().toString())
                .setCustomerId(order.getCustomerId().getValue().toString())
                .setOrderId(order.getId().getValue().toString())
                .setPrice(order.getPrice().getAmount())
                .setCreatedAt(orderCreatedEvent.getCreatedAt().toInstant())
                .setPaymentOrderStatus(PaymentOrderStatusAvroModel.PENDING)
                .build();
    }

    public PaymentRequestAvroModel mapOrderCancelledEventToPaymentRequestAvroModel(OrderCancelledEvent orderCancelledEvent) {

        var order = orderCancelledEvent.getOrder();

        return PaymentRequestAvroModel.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setSagaId(UUID.randomUUID().toString())
                .setCustomerId(order.getCustomerId().getValue().toString())
                .setOrderId(order.getId().getValue().toString())
                .setPrice(order.getPrice().getAmount())
                .setCreatedAt(orderCancelledEvent.getCreatedAt().toInstant())
                .setPaymentOrderStatus(PaymentOrderStatusAvroModel.CANCELLED)
                .build();

    }

    public RestaurantApprovalRequestAvroModel mapOrderPaidEventToRestaurantApprovalRequestAvroModel(OrderPaidEvent orderPaidEvent) {
        var order = orderPaidEvent.getOrder();

        return RestaurantApprovalRequestAvroModel.newBuilder()
                .setId(UUID.randomUUID().toString()) //TODO: check these, perhaps convert to Strings as in tutorial?
                .setSagaId(UUID.randomUUID().toString())
                .setOrderId(order.getId().getValue().toString())
                .setRestaurantId(order.getRestaurantId().getValue().toString())
                .setOrderId(order.getId().getValue().toString())
                .setRestaurantOrderStatus(RestaurantOrderStatusAvroModel.valueOf(order.getOrderStatus().name()))
                .setProducts(order.getItems().stream().map(orderItem -> ProductAvroModel.newBuilder()
                        .setId(orderItem.getProduct().getId().getValue().toString())
                        .setQuantity(orderItem.getQuantity())
                        .build()).collect(Collectors.toList()))
                .setPrice(order.getPrice().getAmount())
                .setCreatedAt(orderPaidEvent.getCreatedAt().toInstant())
                .setRestaurantOrderStatus(RestaurantOrderStatusAvroModel.PAID)
                .build();
    }

    public PaymentResponseMsg mapPaymentResponseAvroModelToPaymentResponse(PaymentResponseAvroModel paymentResponseAvroModel) {
        return PaymentResponseMsg.builder()
                .id(paymentResponseAvroModel.getId())
                .sagaId(paymentResponseAvroModel.getSagaId())
                .paymentId(paymentResponseAvroModel.getPaymentId())
                .customerId(paymentResponseAvroModel.getCustomerId())
                .orderId(paymentResponseAvroModel.getOrderId())
                .price(paymentResponseAvroModel.getPrice())
                .createdAt(paymentResponseAvroModel.getCreatedAt())
                .paymentStatus(PaymentStatus.valueOf(paymentResponseAvroModel.getPaymentStatus().name()))
                .failureMessages(paymentResponseAvroModel.getFailureMessages())
                .build();
    }

    public RestaurantApprovalResponseMsg mapApprovalResponseAvroModelToApprovalResponse(RestaurantApprovalResponseAvroModel avroModel) {
        return RestaurantApprovalResponseMsg.builder()
                .id(avroModel.getId())
                .sagaId(avroModel.getSagaId())
                .restaurantId(avroModel.getRestaurantId())
                .orderId(avroModel.getOrderId())
                .createdAt(avroModel.getCreatedAt())
                .orderApprovalStatus(OrderApprovalStatus.valueOf(avroModel.getOrderApprovalStatus().name()))
                .failureMessages(avroModel.getFailureMessages())
                .build();
    }
}
