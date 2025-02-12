package com.nttdata.food.ordering.system.order.service.app.rest;

import com.nttdata.food.ordering.system.order.service.domain.dto.create.CreateOrderCommandDTO;
import com.nttdata.food.ordering.system.order.service.domain.dto.create.CreateOrderResponseDTO;
import com.nttdata.food.ordering.system.order.service.domain.dto.track.TrackOrderQueryDTO;
import com.nttdata.food.ordering.system.order.service.domain.dto.track.TrackOrderResponseDTO;
import com.nttdata.food.ordering.system.order.service.domain.ports.input.service.OrderApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(value = "/orders", produces = "application/vnd.api.v1+json")
public class OrderController {

    private final OrderApplicationService orderApplicationService;

    @Autowired
    public OrderController(OrderApplicationService orderApplicationService) {
        this.orderApplicationService = orderApplicationService;
    }

    @PostMapping
    public ResponseEntity<CreateOrderResponseDTO> createOrder(@RequestBody CreateOrderCommandDTO createOrderCommandDTO) {
        log.info("Create order for customer {} at restaurant {}", createOrderCommandDTO.getCustomerId(), createOrderCommandDTO.getRestaurantId());
        var createOrderResponseDTO = orderApplicationService.createOrder(createOrderCommandDTO);
        log.info("Order created with tracking id {}", createOrderResponseDTO.getOrderTrackingId());
        return ResponseEntity.ok(createOrderResponseDTO);
    }

    @GetMapping("/{trackingId}")
    public ResponseEntity<TrackOrderResponseDTO> getOrderTrackingById(@PathVariable UUID trackingId) {
        var trackOrderResponseDTO = orderApplicationService.trackOrder(TrackOrderQueryDTO.builder().orderTrackingId(trackingId).build());
        log.info("Returning order status with tracking id {}", trackOrderResponseDTO);
        return ResponseEntity.ok(trackOrderResponseDTO);
    }
}
