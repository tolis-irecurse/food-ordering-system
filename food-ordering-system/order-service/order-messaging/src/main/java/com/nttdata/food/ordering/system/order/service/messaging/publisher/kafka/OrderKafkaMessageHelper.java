package com.nttdata.food.ordering.system.order.service.messaging.publisher.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.function.BiConsumer;

@Slf4j
@Component
public class OrderKafkaMessageHelper {

    public <T> BiConsumer<SendResult<String, T>, Throwable> getKafkaCallback(String responseTopicName, T message, String orderId, Class avroModel) {
        return (result, ex) -> {
            if (ex == null) {
                RecordMetadata metadata = result.getRecordMetadata();
                log.info("Received successful response from Kafka for order id {}. Topic: {}; Partition {}; Offset {}; Timestamp {}, at time {}",
                        orderId,
                        metadata.topic(),
                        metadata.partition(),
                        metadata.offset(),
                        metadata.timestamp(),
                        System.nanoTime()
                );
            } else {
                log.error("Error while sending {} message {} to topic {}", avroModel.getCanonicalName(), message.toString(), responseTopicName, ex);
            }
        };
    }
}
