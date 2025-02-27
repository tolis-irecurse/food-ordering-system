package com.nttdata.food.ordering.system.order.service.messaging.listener.kafka;

import com.nttdata.food.ordering.system.kafka.consumer.KafkaConsumer;
import com.nttdata.food.ordering.system.kafka.order.avro.model.OrderApprovalStatusAvroModel;
import com.nttdata.food.ordering.system.kafka.order.avro.model.RestaurantApprovalResponseAvroModel;
import com.nttdata.food.ordering.system.order.service.domain.ports.input.message.listener.restaurantapproval.RestaurantApprovalResponseMessageListener;
import com.nttdata.food.ordering.system.order.service.messaging.mapper.OrderMessagingDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.nttdata.food.ordering.system.service.domain.model.entity.Order.FAILURE_MESSAGE_DELIMITER;

@Slf4j
@Component
public class RestaurantApprovalResponseKafkaListener implements KafkaConsumer<RestaurantApprovalResponseAvroModel> {

    private final RestaurantApprovalResponseMessageListener restaurantApprovalResponseMessageListener;
    private final OrderMessagingDataMapper orderMessagingDataMapper;

    @Autowired
    public RestaurantApprovalResponseKafkaListener(RestaurantApprovalResponseMessageListener restaurantApprovalResponseMessageListener, OrderMessagingDataMapper orderMessagingDataMapper) {
        this.restaurantApprovalResponseMessageListener = restaurantApprovalResponseMessageListener;
        this.orderMessagingDataMapper = orderMessagingDataMapper;
    }

    @Override
    @KafkaListener(id = "${kafka-consumer-config.restaurant-approval-consumer-group-id}",
            topics = "${order-service.restaurant-approval-response-topic-name}")
    public void receive(@Payload List<RestaurantApprovalResponseAvroModel> messages,
                        @Header(KafkaHeaders.RECEIVED_KEY) List<String> keys,
                        @Header(KafkaHeaders.PARTITION) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {

        log.info("{} number of restaurant approval response messages received with keys {}, partitions{} and offsets {}",
                messages.size(), keys.toString(), partitions.toString(), offsets.toString());

        messages.forEach(message -> {
            if (OrderApprovalStatusAvroModel.APPROVED == message.getOrderApprovalStatus()) {
                log.info("Processing approved order for order id {}", message.getOrderId());

                restaurantApprovalResponseMessageListener.orderApproved(orderMessagingDataMapper.mapApprovalResponseAvroModelToApprovalResponse(message));
            } else if (OrderApprovalStatusAvroModel.REJECTED == message.getOrderApprovalStatus()) {
                log.info("Processing rejected order for order id {} with failure messages {}", message.getOrderId(),
                        String.join(FAILURE_MESSAGE_DELIMITER, message.getFailureMessages())
                );
                restaurantApprovalResponseMessageListener.orderRejected(orderMessagingDataMapper.mapApprovalResponseAvroModelToApprovalResponse(message));
            }


        });
    }
}
