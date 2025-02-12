package com.nttdata.food.ordering.system.kafka.producer.service;

import com.nttdata.food.ordering.system.kafka.producer.exception.KafkaProducerException;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

@Slf4j
@Component
public class KafkaProducerImpl<K extends Serializable, V extends SpecificRecordBase> implements KafkaProducer<K, V> {

    private final KafkaTemplate<K, V> kafkaTemplate;

    @Autowired
    public KafkaProducerImpl(KafkaTemplate<K, V> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    //TODO: Revisit to see if ListenableFuture changes are ok (probably not)
    @Override
    public void send(String topicName, K key, V message, BiConsumer<SendResult<K, V>, Throwable> callback) {
        log.info("Sending message {} to topic {}", message, topicName);

        CompletableFuture<SendResult<K, V>> future = kafkaTemplate.send(topicName, key, message);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                if (callback != null) {
                    callback.accept(result, null);
                }
            } else {
                log.error("Failed to send message with key {}, message {} and exception {}", key, message, ex.getMessage(), ex);

                throw new KafkaProducerException("Error on kafka producer with key: " + key + " and message: " + message);
                // Custom exception handling
                //handleException(ex, topicName, key, message);
                //if (callback != null) {
                //    callback.accept(null, ex);
                //}
            }

        });
    }

    @PreDestroy
    public void close() {
        if (kafkaTemplate != null) {
            log.info("Closing kafka producer!");
            kafkaTemplate.destroy();
        }
    }
}
