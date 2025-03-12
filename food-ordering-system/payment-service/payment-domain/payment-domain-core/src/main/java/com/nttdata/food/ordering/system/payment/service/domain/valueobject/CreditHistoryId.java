package com.nttdata.food.ordering.system.payment.service.domain.valueobject;


import com.nttdata.food.ordering.system.common.domain.valueobject.BaseId;

import java.util.UUID;

public class CreditHistoryId extends BaseId<UUID> {
    public CreditHistoryId(UUID value) {
        super(value);
    }
}
