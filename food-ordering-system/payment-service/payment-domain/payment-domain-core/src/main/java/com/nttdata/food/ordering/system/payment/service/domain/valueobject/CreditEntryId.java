package com.nttdata.food.ordering.system.payment.service.domain.valueobject;

import com.nttdata.food.ordering.system.common.domain.valueobject.BaseId;

import java.util.UUID;

public class CreditEntryId extends BaseId<UUID> {
    public CreditEntryId(UUID value) {
        super(value);
    }
}
