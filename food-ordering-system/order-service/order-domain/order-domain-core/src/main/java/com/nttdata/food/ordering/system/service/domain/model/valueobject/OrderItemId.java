package com.nttdata.food.ordering.system.service.domain.model.valueobject;

import com.nttdata.food.ordering.system.common.domain.valueobject.BaseId;

public class OrderItemId extends BaseId<Long> {
    public OrderItemId(Long value) {
        super(value);
    }
}
