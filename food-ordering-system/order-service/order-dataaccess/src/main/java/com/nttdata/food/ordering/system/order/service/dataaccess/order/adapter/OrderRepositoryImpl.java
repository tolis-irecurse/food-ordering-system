package com.nttdata.food.ordering.system.order.service.dataaccess.order.adapter;

import com.nttdata.food.ordering.system.common.domain.valueobject.OrderId;
import com.nttdata.food.ordering.system.order.service.dataaccess.order.mapper.OrderDataAccessMapper;
import com.nttdata.food.ordering.system.order.service.dataaccess.order.repository.OrderJpaRepository;
import com.nttdata.food.ordering.system.order.service.domain.ports.output.repository.OrderRepository;
import com.nttdata.food.ordering.system.service.domain.model.entity.Order;
import com.nttdata.food.ordering.system.service.domain.model.valueobject.TrackingId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;
    private final OrderDataAccessMapper orderDataAccessMapper;

    @Autowired
    public OrderRepositoryImpl(OrderJpaRepository orderJpaRepository,
                               OrderDataAccessMapper orderDataAccessMapper) {
        this.orderJpaRepository = orderJpaRepository;
        this.orderDataAccessMapper = orderDataAccessMapper;
    }

    @Override
    public Order save(Order order) {

        var orderEntity = orderDataAccessMapper.orderToOrderEntity(order);

         orderJpaRepository.save(orderEntity);

        return orderDataAccessMapper.orderEntityToOrder(orderEntity);
    }

    @Override
    public Optional<Order> findById(OrderId orderId) {
        return orderJpaRepository.findById(orderId.getValue()).map(orderDataAccessMapper::orderEntityToOrder);
    }

    @Override
    public Optional<Order> findByTrackingId(TrackingId trackingId) {
        return orderJpaRepository.findByTrackingId(trackingId.getValue())
                .map(orderDataAccessMapper::orderEntityToOrder);
    }
}
