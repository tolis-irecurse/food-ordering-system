package com.nttdata.food.order.service.domain.entity;

import com.nttdata.food.ordering.system.domain.entity.BaseEntity;
import com.nttdata.food.ordering.system.domain.valueobject.Money;
import com.nttdata.food.ordering.system.domain.valueobject.ProductId;

public class Product extends BaseEntity<ProductId> {
    private String name;
    private Money price;

    public Product(ProductId productId, String name, Money price) {
        super.setId(productId);
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Money getPrice() {
        return price;
    }
}
