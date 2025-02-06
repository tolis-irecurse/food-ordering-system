package com.nttdata.food.ordering.system.order.service.domain.ports.input.message.listener.restaurantapproval;

import com.nttdata.food.ordering.system.order.service.domain.dto.message.RestaurantApprovalResponseMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Service
public class RestaurantApprovalResponseMessageListenerImpl implements RestaurantApprovalResponseMessageListener {

    @Override
    public void orderApproved(RestaurantApprovalResponseMsg restaurantApprovalResponse) {

    }

    @Override
    public void orderRejected(RestaurantApprovalResponseMsg restaurantApprovalResponse) {

    }
}
