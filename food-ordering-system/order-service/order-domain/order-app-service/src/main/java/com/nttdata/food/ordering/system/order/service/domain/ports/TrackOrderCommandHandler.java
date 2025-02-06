package com.nttdata.food.ordering.system.order.service.domain.ports;

import com.nttdata.food.ordering.system.order.service.domain.dto.track.TrackOrderQueryDTO;
import com.nttdata.food.ordering.system.order.service.domain.dto.track.TrackOrderResponseDTO;
import com.nttdata.food.ordering.system.order.service.domain.mapper.OrderDataMapper;
import com.nttdata.food.ordering.system.order.service.domain.ports.output.repository.OrderRepository;
import com.nttdata.food.ordering.system.service.domain.exception.OrderNotFoundException;
import com.nttdata.food.ordering.system.service.domain.model.valueobject.TrackingId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class TrackOrderCommandHandler {

    private final OrderDataMapper orderDataMapper;

    private final OrderRepository orderRepository;

    public TrackOrderCommandHandler(OrderDataMapper orderDataMapper, OrderRepository orderRepository) {
        this.orderDataMapper = orderDataMapper;
        this.orderRepository = orderRepository;
    }

    @Transactional(readOnly = true)
    public TrackOrderResponseDTO trackOrder(TrackOrderQueryDTO trackOrderQuery) {

        return
            orderRepository.findByTrackingId(new TrackingId(trackOrderQuery.getOrderTrackingId()))
                .map(orderDataMapper::mapOrderToTrackOrderResponseDTO)
                .orElseThrow( () -> {
                    log.warn("Could not find order with tracking id {}", trackOrderQuery.getOrderTrackingId());
                    return new OrderNotFoundException("Could not find order with tracking id: " + trackOrderQuery.getOrderTrackingId());
                });
    }
}
