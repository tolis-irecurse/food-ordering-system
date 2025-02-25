package com.nttdata.food.ordering.system.order.service.domain;

import com.nttdata.food.ordering.system.service.domain.OrderDomainService;
import com.nttdata.food.ordering.system.service.domain.OrderDomainServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public OrderDomainService orderDomainService() {
        return new OrderDomainServiceImpl();
    }
}
