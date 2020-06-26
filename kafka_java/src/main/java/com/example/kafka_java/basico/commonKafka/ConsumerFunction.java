package com.example.kafka_java.basico.commonKafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface ConsumerFunction<T> {

    void consume(ConsumerRecord<String, T> record);

}
