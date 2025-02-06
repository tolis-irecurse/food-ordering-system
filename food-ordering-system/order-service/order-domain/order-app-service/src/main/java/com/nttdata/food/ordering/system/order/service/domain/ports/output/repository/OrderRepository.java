package com.nttdata.food.ordering.system.order.service.domain.ports.output.repository;

import com.nttdata.food.ordering.system.service.domain.model.entity.Order;
import com.nttdata.food.ordering.system.service.domain.model.valueobject.TrackingId;

import java.util.Optional;

public interface OrderRepository {

    Order save (Order order);

    Optional<Order> findByTrackingId(TrackingId trackingId);
}
