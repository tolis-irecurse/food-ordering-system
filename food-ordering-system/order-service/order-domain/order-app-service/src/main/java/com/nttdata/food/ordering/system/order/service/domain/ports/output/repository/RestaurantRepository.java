package com.nttdata.food.ordering.system.order.service.domain.ports.output.repository;

import com.nttdata.food.ordering.system.domain.valueobject.RestaurantId;
import com.nttdata.food.ordering.system.service.domain.entity.Restaurant;

import java.util.Optional;

public interface RestaurantRepository {
    Optional<Restaurant> findRestaurantInformation(RestaurantId restaurantId);

}
