package com.nttdata.food.ordering.system.service.domain.model.entity;

import com.nttdata.food.ordering.system.common.domain.entity.AggregateRoot;
import com.nttdata.food.ordering.system.common.domain.valueobject.CustomerId;

public class Customer extends AggregateRoot<CustomerId> {

    public Customer() {

    }

    public Customer(CustomerId customerId) {
        super.setId(customerId);
    }
}
