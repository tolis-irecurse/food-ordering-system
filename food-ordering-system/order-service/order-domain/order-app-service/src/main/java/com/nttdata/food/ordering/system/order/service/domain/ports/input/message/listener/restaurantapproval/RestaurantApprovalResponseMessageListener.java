package com.nttdata.food.ordering.system.order.service.domain.ports.input.message.listener.restaurantapproval;

import com.nttdata.food.ordering.system.order.service.domain.dto.message.RestaurantApprovalResponseMsg;

public interface RestaurantApprovalResponseMessageListener {

    void orderApproved(RestaurantApprovalResponseMsg restaurantApprovalResponse);

    void orderRejected(RestaurantApprovalResponseMsg restaurantApprovalResponse);
}
