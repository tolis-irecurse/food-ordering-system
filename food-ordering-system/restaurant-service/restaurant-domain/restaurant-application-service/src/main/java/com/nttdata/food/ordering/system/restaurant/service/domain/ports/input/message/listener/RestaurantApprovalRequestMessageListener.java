package com.nttdata.food.ordering.system.restaurant.service.domain.ports.input.message.listener;

import com.nttdata.food.ordering.system.restaurant.service.domain.dto.RestaurantApprovalRequest;

public interface RestaurantApprovalRequestMessageListener {
    void approveOrder(RestaurantApprovalRequest restaurantApprovalRequest);
}
