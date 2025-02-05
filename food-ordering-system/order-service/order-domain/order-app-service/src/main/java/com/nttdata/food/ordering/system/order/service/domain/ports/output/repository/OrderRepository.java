package com.nttdata.food.ordering.system.order.service.domain.ports.output.repository;

import com.nttdata.food.ordering.system.service.domain.entity.Order;
import com.nttdata.food.ordering.system.service.domain.valueobject.TrackingId;

import java.util.Optional;

public interface OrderRepository {

    Order save (Order order);

    Optional<Order> findByTrackingId(TrackingId trackingId);
}
