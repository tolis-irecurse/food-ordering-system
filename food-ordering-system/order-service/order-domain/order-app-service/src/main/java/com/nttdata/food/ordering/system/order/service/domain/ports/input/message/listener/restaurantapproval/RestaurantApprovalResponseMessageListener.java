package com.nttdata.food.ordering.system.order.service.domain.ports.input.message.listener.restaurantapproval;

import com.nttdata.food.ordering.system.order.service.domain.payload.message.PaymentResponse;
import com.nttdata.food.ordering.system.order.service.domain.payload.message.RestaurantApprovalResponse;

public interface RestaurantApprovalResponseMessageListener {

    void orderApproved(RestaurantApprovalResponse restaurantApprovalResponse);

    void orderRejected(RestaurantApprovalResponse restaurantApprovalResponse);
}
