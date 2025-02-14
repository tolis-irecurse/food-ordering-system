package com.nttdata.food.ordering.system.order.service.messaging.publisher.kafka;

import com.nttdata.food.ordering.system.kafka.order.avro.model.PaymentRequestAvroModel;
import com.nttdata.food.ordering.system.kafka.producer.service.KafkaProducer;
import com.nttdata.food.ordering.system.order.service.domain.config.OrderServiceConfigData;
import com.nttdata.food.ordering.system.order.service.domain.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;
import com.nttdata.food.ordering.system.order.service.messaging.mapper.OrderMessagingDataMapper;
import com.nttdata.food.ordering.system.service.domain.event.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CreateOrderKafkaMessagePublisher implements OrderCreatedPaymentRequestMessagePublisher {

    private final OrderMessagingDataMapper orderMessagingDataMapper;
    private final OrderServiceConfigData orderServiceConfigData;
    private final KafkaProducer<String, PaymentRequestAvroModel> kafkaProducer;
    private final OrderKafkaMessageHelper orderKafkaMessageHelper;

    @Autowired
    public CreateOrderKafkaMessagePublisher(OrderMessagingDataMapper orderMessagingDataMapper, OrderServiceConfigData orderServiceConfigData, KafkaProducer<String, PaymentRequestAvroModel> kafkaProducer, OrderKafkaMessageHelper orderKafkaMessageHelper) {
        this.orderMessagingDataMapper = orderMessagingDataMapper;
        this.orderServiceConfigData = orderServiceConfigData;
        this.kafkaProducer = kafkaProducer;
        this.orderKafkaMessageHelper = orderKafkaMessageHelper;
    }

    @Override
    public void publish(OrderCreatedEvent domainEvent) {
        var orderId = domainEvent.getOrder().getId().getValue().toString();
        log.info("Received OrderCreatedEvent for order id: {}", orderId);

        try {

            var paymentRequestAvroModel = orderMessagingDataMapper.mapOrderCreatedEventToPaymentRequestAvroModel(domainEvent);

            kafkaProducer.send(orderServiceConfigData.getPaymentRequestTopicName(),
                    orderId,
                    paymentRequestAvroModel,
                    orderKafkaMessageHelper.getKafkaCallback(orderServiceConfigData.getPaymentResponseTopicName(), paymentRequestAvroModel,
                            orderId, PaymentRequestAvroModel.class)
            );

            log.info("Sent OrderCreatedEvent to Kafka for order id: {}", orderId);
        } catch (Exception e) {
            log.error("Error while sending PaymentRequestAvroModel message to Kafka with order id: {}, error: {}", orderId, e.getMessage());
        }
    }
}
