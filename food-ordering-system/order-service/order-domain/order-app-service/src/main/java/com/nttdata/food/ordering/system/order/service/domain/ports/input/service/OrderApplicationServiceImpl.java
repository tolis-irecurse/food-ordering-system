package com.nttdata.food.ordering.system.order.service.domain.ports.input.service;

import com.nttdata.food.ordering.system.order.service.domain.payload.create.CreateOrderCommand;
import com.nttdata.food.ordering.system.order.service.domain.payload.create.CreateOrderResponse;
import com.nttdata.food.ordering.system.order.service.domain.payload.track.TrackOrderQuery;
import com.nttdata.food.ordering.system.order.service.domain.payload.track.TrackOrderResponse;
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
    public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand) {
        return orderCreateCommandHandler.createOrder(createOrderCommand);
    }

    @Override
    public TrackOrderResponse trackOrder(TrackOrderQuery trackOrderQuery) {
        return trackOrderCommandHandler.trackOrder(trackOrderQuery);
    }
}
