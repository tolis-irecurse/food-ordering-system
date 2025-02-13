package com.nttdata.food.ordering.system.order.service.dataaccess.customer.mapper;

import com.nttdata.food.ordering.system.common.domain.valueobject.CustomerId;
import com.nttdata.food.ordering.system.order.service.dataaccess.customer.entity.CustomerEntity;
import com.nttdata.food.ordering.system.service.domain.model.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerDataAccessMapper {

    public Customer customerEntityToCustomer(CustomerEntity customerEntity) {
        return new Customer(new CustomerId(customerEntity.getId()));
    }
}
