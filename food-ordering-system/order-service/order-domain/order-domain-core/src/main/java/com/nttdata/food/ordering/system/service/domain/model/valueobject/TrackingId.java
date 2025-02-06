package com.nttdata.food.ordering.system.service.domain.model.valueobject;

import com.nttdata.food.ordering.system.common.domain.valueobject.BaseId;

import java.util.UUID;

public class TrackingId extends BaseId<UUID> {
    public TrackingId(UUID value) {
        super(value);
    }
}
