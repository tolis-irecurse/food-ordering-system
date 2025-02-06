package com.nttdata.food.ordering.system.order.service.domain.ports.input.service;

import com.nttdata.food.ordering.system.order.service.domain.dto.create.CreateOrderCommandDTO;
import com.nttdata.food.ordering.system.order.service.domain.dto.create.CreateOrderResponseDTO;
import com.nttdata.food.ordering.system.order.service.domain.dto.track.TrackOrderQueryDTO;
import com.nttdata.food.ordering.system.order.service.domain.dto.track.TrackOrderResponseDTO;
import jakarta.validation.Valid;

public interface OrderApplicationService {
    CreateOrderResponseDTO createOrder(@Valid CreateOrderCommandDTO createOrderCommand);
    TrackOrderResponseDTO trackOrder(@Valid TrackOrderQueryDTO trackOrderQuery);
}
