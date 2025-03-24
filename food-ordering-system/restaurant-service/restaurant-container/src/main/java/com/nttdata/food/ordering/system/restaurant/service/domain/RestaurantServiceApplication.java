package com.nttdata.food.ordering.system.restaurant.service.domain;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = { "com.nttdata.food.ordering.system.restaurant.service.dataaccess", "com.nttdata.food.ordering.system.dataaccess" })
@EntityScan(basePackages = { "com.nttdata.food.ordering.system.restaurant.service.dataaccess", "com.nttdata.food.ordering.system.dataaccess" })
@SpringBootApplication(scanBasePackages = "com.nttdata.food.ordering.system")
public class RestaurantServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(RestaurantServiceApplication.class, args);
    }
}
