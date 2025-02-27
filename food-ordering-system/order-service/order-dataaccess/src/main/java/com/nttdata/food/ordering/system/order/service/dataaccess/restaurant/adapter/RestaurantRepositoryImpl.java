package com.nttdata.food.ordering.system.order.service.dataaccess.restaurant.adapter;

import com.nttdata.food.ordering.system.order.service.dataaccess.restaurant.entity.RestaurantEntity;
import com.nttdata.food.ordering.system.order.service.dataaccess.restaurant.mapper.RestaurantDataAccessMapper;
import com.nttdata.food.ordering.system.order.service.dataaccess.restaurant.repository.RestaurantJpaRepository;
import com.nttdata.food.ordering.system.order.service.domain.ports.output.repository.RestaurantRepository;
import com.nttdata.food.ordering.system.service.domain.model.entity.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class RestaurantRepositoryImpl implements RestaurantRepository {

    private final RestaurantJpaRepository restaurantJpaRepository;
    private final RestaurantDataAccessMapper restaurantDataAccessMapper;

    @Autowired
    public RestaurantRepositoryImpl(RestaurantJpaRepository restaurantJpaRepository, RestaurantDataAccessMapper restaurantDataAccessMapper) {
        this.restaurantJpaRepository = restaurantJpaRepository;
        this.restaurantDataAccessMapper = restaurantDataAccessMapper;
    }


    @Override
    public Optional<Restaurant> findRestaurantInformation(Restaurant restaurant) {
        List<UUID> restaurantProducts =
            restaurantDataAccessMapper.mapRestaurantToRestaurantProducts(restaurant);

        Optional<List<RestaurantEntity>> restaurantEntities = restaurantJpaRepository
                .findByRestaurantIdAndProductIdIn(restaurant.getId().getValue(), restaurantProducts);

        return restaurantEntities.map(restaurantDataAccessMapper::mapRestaurantEntityToRestaurant);
    }
}
