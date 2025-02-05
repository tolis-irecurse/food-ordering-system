package com.nttdata.food.order.service.domain.valueobject;

import com.nttdata.food.ordering.system.domain.valueobject.BaseId;

public class OrderItemId extends BaseId<Long> {
    public OrderItemId(Long value) {
        super(value);
    }
}
