package com.nttdata.food.ordering.system.order.service.dataaccess.customer.adapter;

import com.nttdata.food.ordering.system.order.service.dataaccess.customer.mapper.CustomerDataAccessMapper;
import com.nttdata.food.ordering.system.order.service.dataaccess.customer.repository.CustomerJpaRepository;
import com.nttdata.food.ordering.system.order.service.domain.ports.output.repository.CustomerRepository;
import com.nttdata.food.ordering.system.service.domain.model.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class CustomerRepositoryImpl implements CustomerRepository {

    private final CustomerJpaRepository customerJpaRepository;
    private final CustomerDataAccessMapper customerDataAccessMapper;

    @Autowired
    public CustomerRepositoryImpl(CustomerJpaRepository customerJpaRepository, CustomerDataAccessMapper customerDataAccessMapper) {
        this.customerJpaRepository = customerJpaRepository;
        this.customerDataAccessMapper = customerDataAccessMapper;
    }

    @Override
    public Optional<Customer> findCustomer(UUID customerId) {
        return customerJpaRepository.findById(customerId).map(customerDataAccessMapper::customerEntityToCustomer);
    }
}
