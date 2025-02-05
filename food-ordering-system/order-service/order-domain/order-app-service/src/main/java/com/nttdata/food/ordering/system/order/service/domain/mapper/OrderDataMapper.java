package com.nttdata.food.ordering.system.order.service.domain.mapper;

import com.nttdata.food.ordering.system.domain.valueobject.CustomerId;
import com.nttdata.food.ordering.system.domain.valueobject.Money;
import com.nttdata.food.ordering.system.domain.valueobject.ProductId;
import com.nttdata.food.ordering.system.domain.valueobject.RestaurantId;
import com.nttdata.food.ordering.system.order.service.domain.payload.create.CreateOrderCommand;
import com.nttdata.food.ordering.system.order.service.domain.payload.create.CreateOrderResponse;
import com.nttdata.food.ordering.system.order.service.domain.payload.create.OrderAddress;
import com.nttdata.food.ordering.system.service.domain.entity.Order;
import com.nttdata.food.ordering.system.service.domain.entity.OrderItem;
import com.nttdata.food.ordering.system.service.domain.entity.Product;
import com.nttdata.food.ordering.system.service.domain.entity.Restaurant;
import com.nttdata.food.ordering.system.service.domain.valueobject.StreetAddress;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderDataMapper {

    public Restaurant mapCreateOrderCommandToRestaurant(CreateOrderCommand createOrderCommand) {
        return Restaurant.builder()
                .restaurantId(new RestaurantId(createOrderCommand.getRestaurantId()))
                .products(createOrderCommand.getItems().stream()
                        .map( oi -> new Product(new ProductId(oi.getProductId())))
                        .collect(Collectors.toList()))
                .build();
    }

    public Order mapCreateOrderCommandToOrder(CreateOrderCommand createOrderCommand) {
        return Order.builder()
                .customerId(new CustomerId(createOrderCommand.getCustomerId()))
                .restaurantId(new RestaurantId(createOrderCommand.getRestaurantId()))
                .deliveryAddress(mapOrderAddressToStreetAddress(createOrderCommand.getAddress()))
                .price(new Money(createOrderCommand.getPrice()))
                .items(mapOrderItemsToOrderItemEntities(createOrderCommand.getItems()))
                .build();
    }

    public CreateOrderResponse mapOrderToCreateOrderResponse(Order order) {
        return CreateOrderResponse.builder()
                .orderTrackingId(order.getTrackingId().getValue())
                .orderStatus(order.getOrderStatus())
                .build();
    }

    private List<OrderItem> mapOrderItemsToOrderItemEntities(List<com.nttdata.food.ordering.system.order.service.domain.payload.create.OrderItem> orderItems) {
        return orderItems.stream()
                .map(orderItem ->
                    OrderItem.builder()
                            .product(new Product(new ProductId(orderItem.getProductId())))
                            .price(new Money(orderItem.getPrice()))
                            .quantity(orderItem.getQuantity())
                            .subTotal(new Money(orderItem.getSubtotal()))
                            .build()
                )
                .collect(Collectors.toList());
    }

    private StreetAddress mapOrderAddressToStreetAddress(@NotNull OrderAddress orderAddress) {
        return new StreetAddress(
            UUID.randomUUID(),
            orderAddress.getStreet(),
            orderAddress.getPostalCode(),
            orderAddress.getCity()
        );
    }
}
