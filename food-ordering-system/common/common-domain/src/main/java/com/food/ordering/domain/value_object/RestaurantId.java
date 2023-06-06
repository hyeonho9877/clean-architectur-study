package com.food.ordering.domain.value_object;

import java.util.UUID;

public class RestaurantId extends BaseId<UUID> {
    public RestaurantId(UUID value) {
        super(value);
    }
}
