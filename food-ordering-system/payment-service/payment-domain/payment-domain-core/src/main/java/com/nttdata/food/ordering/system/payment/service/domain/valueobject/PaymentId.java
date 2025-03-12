package com.nttdata.food.ordering.system.payment.service.domain.valueobject;


import com.nttdata.food.ordering.system.common.domain.valueobject.BaseId;

import java.util.UUID;

public class PaymentId extends BaseId<UUID> {
    public PaymentId(UUID value) {
        super(value);
    }
}
