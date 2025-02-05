package com.nttdata.food.ordering.system.order.service.domain.ports.input.service;

import com.nttdata.food.ordering.system.order.service.domain.payload.create.CreateOrderCommand;
import com.nttdata.food.ordering.system.order.service.domain.payload.create.CreateOrderResponse;
import com.nttdata.food.ordering.system.order.service.domain.payload.track.TrackOrderQuery;
import com.nttdata.food.ordering.system.order.service.domain.payload.track.TrackOrderResponse;
import jakarta.validation.Valid;

public interface OrderApplicationService {
    CreateOrderResponse createOrder(@Valid CreateOrderCommand createOrderCommand);
    TrackOrderResponse trackOrder(@Valid TrackOrderQuery trackOrderQuery);
}
