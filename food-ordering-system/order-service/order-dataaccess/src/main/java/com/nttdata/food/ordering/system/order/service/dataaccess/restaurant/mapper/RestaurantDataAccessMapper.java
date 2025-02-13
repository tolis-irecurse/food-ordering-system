package com.nttdata.food.ordering.system.order.service.dataaccess.restaurant.mapper;

import com.nttdata.food.ordering.system.common.domain.valueobject.Money;
import com.nttdata.food.ordering.system.common.domain.valueobject.ProductId;
import com.nttdata.food.ordering.system.order.service.dataaccess.restaurant.entity.RestaurantEntity;
import com.nttdata.food.ordering.system.order.service.dataaccess.restaurant.exception.DataAccessErrorCode;
import com.nttdata.food.ordering.system.order.service.dataaccess.restaurant.exception.RestaurantDataAccessException;
import com.nttdata.food.ordering.system.service.domain.model.entity.Product;
import com.nttdata.food.ordering.system.service.domain.model.entity.Restaurant;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class RestaurantDataAccessMapper {

    public List<UUID> mapRestaurantToRestaurantProducts(Restaurant restaurant) {

        return restaurant.getProducts().stream()
                .map(product -> product.getId().getValue())
                .collect(Collectors.toList());
    }

    public Restaurant mapRestaurantEntityToRestaurant(List<RestaurantEntity> restaurantEntities) {

        var restaurantEntity =
                restaurantEntities.stream()
                .findFirst()
                .orElseThrow( () -> new RestaurantDataAccessException(DataAccessErrorCode.RESTAURANT_NOT_FOUND));

        var restaurantProducts = restaurantEntities.stream().map(entity ->
                new Product(
                    new ProductId(entity.getProductId()),
                    entity.getProductName(),
                    new Money(entity.getProductPrice()))
                )
                .toList();

        return Restaurant.builder()
                .products(restaurantProducts)
                .active(restaurantEntity.getRestaurantActive())
                .build();
    }
}
