package com.nttdata.food.ordering.system.order.service.domain.ports.output.repository;

import com.nttdata.food.ordering.system.service.domain.model.entity.Customer;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Optional<Customer> findCustomer(UUID customerId);
}
