package com.nttdata.food.ordering.system.order.service.domain.mapper;

import com.nttdata.food.ordering.system.common.domain.valueobject.CustomerId;
import com.nttdata.food.ordering.system.common.domain.valueobject.Money;
import com.nttdata.food.ordering.system.common.domain.valueobject.ProductId;
import com.nttdata.food.ordering.system.common.domain.valueobject.RestaurantId;
import com.nttdata.food.ordering.system.order.service.domain.dto.create.CreateOrderCommandDTO;
import com.nttdata.food.ordering.system.order.service.domain.dto.create.CreateOrderResponseDTO;
import com.nttdata.food.ordering.system.order.service.domain.dto.create.OrderAddressDTO;
import com.nttdata.food.ordering.system.order.service.domain.dto.create.OrderItemDTO;
import com.nttdata.food.ordering.system.order.service.domain.dto.track.TrackOrderResponseDTO;
import com.nttdata.food.ordering.system.service.domain.model.entity.Order;
import com.nttdata.food.ordering.system.service.domain.model.entity.OrderItem;
import com.nttdata.food.ordering.system.service.domain.model.entity.Product;
import com.nttdata.food.ordering.system.service.domain.model.entity.Restaurant;
import com.nttdata.food.ordering.system.service.domain.model.valueobject.StreetAddress;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderDataMapper {

    public Restaurant mapCreateOrderCommandDTOToRestaurant(CreateOrderCommandDTO createOrderCommand) {
        return Restaurant.builder()
                .restaurantId(new RestaurantId(createOrderCommand.getRestaurantId()))
                .products(createOrderCommand.getItems().stream()
                        .map( oi -> new Product(new ProductId(oi.getProductId())))
                        .collect(Collectors.toList()))
                .build();
    }

    public Order mapCreateOrderCommandDTOToOrder(CreateOrderCommandDTO createOrderCommand) {
        return Order.builder()
                .customerId(new CustomerId(createOrderCommand.getCustomerId()))
                .restaurantId(new RestaurantId(createOrderCommand.getRestaurantId()))
                .deliveryAddress(mapOrderAddressDTOToStreetAddress(createOrderCommand.getAddress()))
                .price(new Money(createOrderCommand.getPrice()))
                .items(mapOrderItemDTOsToOrderItems(createOrderCommand.getItems()))
                .build();
    }

    public CreateOrderResponseDTO mapOrderToCreateOrderResponseDTO(Order order) {
        return CreateOrderResponseDTO.builder()
                .orderTrackingId(order.getTrackingId().getValue())
                .orderStatus(order.getOrderStatus())
                .build();
    }

    public TrackOrderResponseDTO mapOrderToTrackOrderResponseDTO(Order order) {
        return TrackOrderResponseDTO.builder()
                .orderTrackingId(order.getTrackingId().getValue())
                .orderStatus(order.getOrderStatus())
                .failureMessages(order.getFailureMessages())
                .build();
    }

    private List<OrderItem> mapOrderItemDTOsToOrderItems(List<OrderItemDTO> orderItems) {
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

    private StreetAddress mapOrderAddressDTOToStreetAddress(@NotNull OrderAddressDTO orderAddress) {
        return new StreetAddress(
            UUID.randomUUID(),
            orderAddress.getStreet(),
            orderAddress.getPostalCode(),
            orderAddress.getCity()
        );
    }
}
