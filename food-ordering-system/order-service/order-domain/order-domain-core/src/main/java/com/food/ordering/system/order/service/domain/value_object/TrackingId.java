package com.food.ordering.system.order.service.domain.value_object;

import com.food.ordering.domain.value_object.BaseId;

import java.util.UUID;

public class TrackingId extends BaseId<UUID> {
    public TrackingId(UUID value) {
        super(value);
    }
}
