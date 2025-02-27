package com.nttdata.food.ordering.system.order.service.messaging.listener.kafka;

import com.nttdata.food.ordering.system.kafka.consumer.KafkaConsumer;
import com.nttdata.food.ordering.system.kafka.order.avro.model.PaymentResponseAvroModel;
import com.nttdata.food.ordering.system.kafka.order.avro.model.PaymentStatusAvroModel;
import com.nttdata.food.ordering.system.order.service.domain.ports.input.message.listener.payment.PaymentResponseMessageListener;
import com.nttdata.food.ordering.system.order.service.messaging.mapper.OrderMessagingDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class PaymentResponseKaflaListener implements KafkaConsumer<PaymentResponseAvroModel> {

    private final PaymentResponseMessageListener paymentResponseMessageListener;
    private final OrderMessagingDataMapper orderMessagingDataMapper;

    @Autowired
    public PaymentResponseKaflaListener(PaymentResponseMessageListener paymentResponseMessageListener, OrderMessagingDataMapper orderMessagingDataMapper) {
        this.paymentResponseMessageListener = paymentResponseMessageListener;
        this.orderMessagingDataMapper = orderMessagingDataMapper;
    }

    @Override
    @KafkaListener(id = "${kafka-consumer-config.payment-consumer-group-id}", topics = "${order-service.payment-response-topic-name}")
    public void receive(@Payload List<PaymentResponseAvroModel> messages,
                        @Header(KafkaHeaders.RECEIVED_KEY) List<String> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {

        log.info("{} number of payment responses received with keys {}, partitions {} and offset {}",
                messages.size(), keys.toString(), partitions.toString(), offsets.toString());

        messages.forEach(message -> {
            if (PaymentStatusAvroModel.COMPLETED.equals(message.getPaymentStatus())) {
                log.info("Processing successful payment for order id {}", message.getOrderId());
                paymentResponseMessageListener.paymentCompleted(orderMessagingDataMapper.mapPaymentResponseAvroModelToPaymentResponse(message));
            } else if (PaymentStatusAvroModel.CANCELLED.equals(message.getPaymentStatus()) || PaymentStatusAvroModel.FAILED.equals(message.getPaymentStatus())) {
                log.info("Processing unsuccessful payment for order id {}", message.getOrderId());
                paymentResponseMessageListener.paymentCancelled(orderMessagingDataMapper.mapPaymentResponseAvroModelToPaymentResponse(message));
            }
        });

    }
}
