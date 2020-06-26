package com.example.kafka_java.basico;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface ConsumerFunction {

    void consume(ConsumerRecord<String, String> record);

}
