package order.service.domain.value_object;

import com.food.ordering.domain.value_object.BaseId;

public class OrderItemId extends BaseId<Long> {
    public OrderItemId(Long value) {
        super(value);
    }
}
