package order.service.domain;

import com.food.ordering.domain.value_object.ProductId;
import order.service.domain.entity.Order;
import order.service.domain.entity.OrderItem;
import order.service.domain.entity.Product;
import order.service.domain.entity.Restaurant;
import order.service.domain.event.OrderCancelledEvent;
import order.service.domain.event.OrderCreatedEvent;
import order.service.domain.event.OrderPaidEvent;
import order.service.domain.exception.OrderDomainException;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class OrderDomainServiceImpl implements OrderDomainService {

    private final Logger log = Logger.getLogger(this.getClass().getSimpleName());
    private static final ZoneId UTC = ZoneId.of("UTC");

    @Override
    public OrderCreatedEvent validateAndInitiateOrder(Order order, Restaurant restaurant) {
        validateRestaurant(restaurant);
        setOrderProductInformation(order, restaurant);
        order.validateOrder();
        order.initializeOrder();
        log.info("Order with id: " + order.getId().getValue() + " is initiated");
        return new OrderCreatedEvent(order, ZonedDateTime.now(UTC));
    }

    private void setOrderProductInformation(Order order, Restaurant restaurant) {
        HashMap<ProductId, Product> restaurantProducts = new HashMap<>();
        restaurant.getProducts().forEach(p -> restaurantProducts.put(p.getId(), p));
        for (OrderItem orderItem : order.getItems()) {
            Product product = orderItem.getProduct();
            if (restaurantProducts.containsKey(product.getId())) {
                Product restaurantProduct = restaurantProducts.get(product.getId());
                product.updateWithConfirmedNameAndPrice(restaurantProduct.getName(), restaurantProduct.getPrice());
            }
        }
    }

    private void validateRestaurant(Restaurant restaurant) {
        if (!restaurant.isActive()) {
            throw new OrderDomainException("Restaurant with id " + restaurant.getId().getValue() + " is currently not active!");
        }
    }

    @Override
    public OrderPaidEvent payOrder(Order order) {
        order.pay();
        log.info("Order with id " + order.getId().getValue() + " is paid!");
        return new OrderPaidEvent(order, ZonedDateTime.now(UTC));
    }

    @Override
    public void approveOrder(Order order) {
        order.approve();
        log.info("Order with id " + order.getId().getValue() + " is approved!");
    }

    @Override
    public OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages) {
        order.initCancel(failureMessages);
        log.info("Order payment is cancelling with id " + order.getId().getValue());
        return new OrderCancelledEvent(order, ZonedDateTime.now(UTC));
    }

    @Override
    public void cancelOrder(Order order, List<String> failureMessages) {
        order.cancel(failureMessages);
        log.info("Order with id " + order.getId().getValue() + " is cancelled");
    }
}
