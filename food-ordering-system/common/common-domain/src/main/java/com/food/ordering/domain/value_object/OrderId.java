package com.food.ordering.domain.value_object;

import java.util.UUID;

public class OrderId extends BaseId<UUID> {

    public OrderId(UUID value) {
        super(value);
    }
}
