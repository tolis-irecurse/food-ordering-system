package com.nttdata.food.ordering.system.order.service.domain.ports.input.service;

import com.nttdata.food.ordering.system.order.service.domain.dto.create.CreateOrderCommandDTO;
import com.nttdata.food.ordering.system.order.service.domain.dto.create.CreateOrderResponseDTO;
import com.nttdata.food.ordering.system.order.service.domain.dto.track.TrackOrderQueryDTO;
import com.nttdata.food.ordering.system.order.service.domain.dto.track.TrackOrderResponseDTO;
import com.nttdata.food.ordering.system.order.service.domain.ports.OrderCreateCommandHandler;
import com.nttdata.food.ordering.system.order.service.domain.ports.TrackOrderCommandHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Service
class OrderApplicationServiceImpl implements OrderApplicationService {

    private final OrderCreateCommandHandler orderCreateCommandHandler;
    private final TrackOrderCommandHandler trackOrderCommandHandler;

    OrderApplicationServiceImpl(OrderCreateCommandHandler orderCreateCommandHandler, TrackOrderCommandHandler trackOrderCommandHandler) {
        this.orderCreateCommandHandler = orderCreateCommandHandler;
        this.trackOrderCommandHandler = trackOrderCommandHandler;
    }

    @Override
    public CreateOrderResponseDTO createOrder(CreateOrderCommandDTO createOrderCommand) {
        return orderCreateCommandHandler.createOrder(createOrderCommand);
    }

    @Override
    public TrackOrderResponseDTO trackOrder(TrackOrderQueryDTO trackOrderQuery) {
        return trackOrderCommandHandler.trackOrder(trackOrderQuery);
    }
}
