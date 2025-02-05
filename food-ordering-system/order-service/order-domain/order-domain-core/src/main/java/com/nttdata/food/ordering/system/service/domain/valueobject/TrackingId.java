package com.nttdata.food.ordering.system.service.domain.valueobject;

import com.nttdata.food.ordering.system.domain.valueobject.BaseId;

import java.util.UUID;

public class TrackingId extends BaseId<UUID> {
    public TrackingId(UUID value) {
        super(value);
    }
}
