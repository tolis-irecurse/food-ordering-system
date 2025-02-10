package com.nttdata.food.ordering.system.order.service.domain;

import com.nttdata.food.ordering.system.common.domain.code.DomainInfoCode;
import com.nttdata.food.ordering.system.common.domain.valueobject.*;
import com.nttdata.food.ordering.system.order.service.domain.dto.create.CreateOrderCommandDTO;
import com.nttdata.food.ordering.system.order.service.domain.dto.create.OrderAddressDTO;
import com.nttdata.food.ordering.system.order.service.domain.dto.create.OrderItemDTO;
import com.nttdata.food.ordering.system.order.service.domain.mapper.OrderDataMapper;
import com.nttdata.food.ordering.system.order.service.domain.ports.input.service.OrderApplicationService;
import com.nttdata.food.ordering.system.order.service.domain.ports.output.repository.CustomerRepository;
import com.nttdata.food.ordering.system.order.service.domain.ports.output.repository.OrderRepository;
import com.nttdata.food.ordering.system.order.service.domain.ports.output.repository.RestaurantRepository;
import com.nttdata.food.ordering.system.service.domain.exception.OrderDomainException;
import com.nttdata.food.ordering.system.service.domain.model.entity.Customer;
import com.nttdata.food.ordering.system.service.domain.model.entity.Order;
import com.nttdata.food.ordering.system.service.domain.model.entity.Product;
import com.nttdata.food.ordering.system.service.domain.model.entity.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.nttdata.food.ordering.system.common.domain.code.DomainErrorCode.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = OrderTestConfiguration.class)
public class OrderApplicationServiceTest {

    @Autowired
    private OrderApplicationService orderApplicationService;

    @Autowired
    private OrderDataMapper orderDataMapper;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    private CreateOrderCommandDTO createOrderCommand;

    private CreateOrderCommandDTO createOrderCommandWrongPrice;

    private CreateOrderCommandDTO createOrderCommandWrongProductPrice;

    private final UUID CUSTOMER_ID = UUID.fromString("81dac710-dd8c-4946-b350-e6ef2ba1190b");
    private final UUID RESTAURANT_ID = UUID.fromString("befab218-2295-4dfa-8a2e-a0d5d2add83c");
    private final UUID PRODUCT_ID = UUID.fromString("899e000b-0363-4db4-9780-0dabbbaee18a");
    private final UUID ORDER_ID = UUID.fromString("a2742c04-861d-4029-ae00-410fa3926ec8");

    private final BigDecimal PRICE = new BigDecimal("200.00");

    @BeforeEach
    public void init() {
        createOrderCommand = CreateOrderCommandDTO.builder()
                .customerId(CUSTOMER_ID)
                .restaurantId(RESTAURANT_ID)
                .address(OrderAddressDTO.builder()
                        .street("street_1")
                        .postalCode("1000AB")
                        .city("Paris")
                        .build()
                )
                .price(PRICE)
                .items(
                    List.of(OrderItemDTO.builder()
                                    .productId(PRODUCT_ID)
                                    .quantity(1)
                                    .price(new BigDecimal("50.00"))
                                    .subtotal(new BigDecimal("50.00"))
                                    .build(),
                            OrderItemDTO.builder()
                                    .productId(PRODUCT_ID)
                                    .quantity(3)
                                    .price(new BigDecimal("50.00"))
                                    .subtotal(new BigDecimal("150.00"))
                                    .build())
                )
                .build();

        createOrderCommandWrongPrice = CreateOrderCommandDTO.builder()
                .customerId(CUSTOMER_ID)
                .restaurantId(RESTAURANT_ID)
                .address(OrderAddressDTO.builder()
                        .street("street_1")
                        .postalCode("1000AB")
                        .city("Paris")
                        .build()
                )
                .price(new BigDecimal("150.00"))
                .items(
                        List.of(OrderItemDTO.builder()
                                        .productId(PRODUCT_ID)
                                        .quantity(1)
                                        .price(new BigDecimal("50.00"))
                                        .subtotal(new BigDecimal("50.00"))
                                        .build(),
                                OrderItemDTO.builder()
                                        .productId(PRODUCT_ID)
                                        .quantity(3)
                                        .price(new BigDecimal("50.00"))
                                        .subtotal(new BigDecimal("150.00"))
                                        .build())
                )
                .build();

        createOrderCommandWrongProductPrice = CreateOrderCommandDTO.builder()
                .customerId(CUSTOMER_ID)
                .restaurantId(RESTAURANT_ID)
                .address(OrderAddressDTO.builder()
                        .street("street_1")
                        .postalCode("1000AB")
                        .city("Paris")
                        .build()
                )
                .price(new BigDecimal("210.00"))
                .items(
                        List.of(OrderItemDTO.builder()
                                        .productId(PRODUCT_ID)
                                        .quantity(1)
                                        .price(new BigDecimal("60.00"))
                                        .subtotal(new BigDecimal("60.00"))
                                        .build(),
                                OrderItemDTO.builder()
                                        .productId(PRODUCT_ID)
                                        .quantity(3)
                                        .price(new BigDecimal("50.00"))
                                        .subtotal(new BigDecimal("150.00"))
                                        .build())
                )
                .build();

        var customer = new Customer();
        customer.setId(new CustomerId(CUSTOMER_ID));

        var restaurant = Restaurant.builder()
                .restaurantId(new RestaurantId(createOrderCommand.getRestaurantId()))
                .products(List.of(
                        new Product(new ProductId(PRODUCT_ID), "product-1", new Money(new BigDecimal("50.00"))),
                        new Product(new ProductId(PRODUCT_ID), "product-2", new Money(new BigDecimal("50.00")))
                ))
                .active(true)
                .build();

        Order order = orderDataMapper.mapCreateOrderCommandDTOToOrder(createOrderCommand);
        order.setId(new OrderId(ORDER_ID));

        when(customerRepository.findCustomer(CUSTOMER_ID)).thenReturn(Optional.of(customer));
        when(restaurantRepository.findRestaurantInformation(orderDataMapper.mapCreateOrderCommandDTOToRestaurant(createOrderCommand).getId())) //TODO: Check why getId() was required
                .thenReturn(Optional.of(restaurant));

        when(orderRepository.save(any(Order.class))).thenReturn(order);
    }

    @Test
    public void testCreateOrder() {
        var createOrderResponse = orderApplicationService.createOrder(createOrderCommand);
        assertEquals(OrderStatus.PENDING, createOrderResponse.getOrderStatus());
        assertEquals(DomainInfoCode.ORDER_CREATED_SUCCESSFULLY.getMessage(), createOrderResponse.getMessage());
        assertNotNull(createOrderResponse.getOrderTrackingId());
    }

    @Test
    public void testCreateOrderWrongTotalPrice() {
        var orderDomainException =
            assertThrows(
                OrderDomainException.class,
                () -> orderApplicationService.createOrder(createOrderCommandWrongPrice)
            );

        assertEquals(ORDER_TOTAL_NE_TO_ORDER_ITEMS_TOTAL, orderDomainException.getDomainErrorCode());
    }

    @Test
    public void testCreateOrderWrongProductPrice() {
        var orderDomainException =
                assertThrows(
                        OrderDomainException.class,
                        () -> orderApplicationService.createOrder(createOrderCommandWrongProductPrice)
                );

        assertEquals(ORDER_ITEM_PRICE_NOT_VALID_FOR_PRODUCT, orderDomainException.getDomainErrorCode());
    }

    @Test
    public void testCreateOrderWithInactiveRestaurant() {
        var restaurant = Restaurant.builder()
            .restaurantId(new RestaurantId(createOrderCommand.getRestaurantId()))
            .products(List.of(
                    new Product(new ProductId(PRODUCT_ID), "product-1", new Money(new BigDecimal("50.00"))),
                    new Product(new ProductId(PRODUCT_ID), "product-2", new Money(new BigDecimal("50.00")))
            ))
            .active(false)
            .build();

        when(restaurantRepository.findRestaurantInformation(orderDataMapper.mapCreateOrderCommandDTOToRestaurant(createOrderCommand).getId()))
                .thenReturn(Optional.of(restaurant));

        var orderDomainException =
                assertThrows(
                        OrderDomainException.class,
                        () -> orderApplicationService.createOrder(createOrderCommand)
                );

        assertEquals(RESTAURANT_NOT_ACTIVE, orderDomainException.getDomainErrorCode());
    }

}
