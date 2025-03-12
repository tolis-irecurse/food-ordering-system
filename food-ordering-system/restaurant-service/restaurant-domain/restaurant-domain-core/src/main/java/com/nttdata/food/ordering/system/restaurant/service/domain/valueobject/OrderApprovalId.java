package com.nttdata.food.ordering.system.restaurant.service.domain.valueobject;

import com.nttdata.food.ordering.system.common.domain.valueobject.BaseId;

import java.util.UUID;

public class OrderApprovalId extends BaseId<UUID> {
    public OrderApprovalId(UUID value) {
        super(value);
    }
}
