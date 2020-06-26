package com.example.kafka_java.basico;

import lombok.var;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import static com.example.kafka_java.Constants.*;

public class FraudDetectorServiceMain {

    public static void main(String[] args) {
        var fraudService = new FraudDetectorServiceMain();
        try (var kafkaService = new KafkaService(FraudDetectorServiceMain.class.getSimpleName(),
                ECOMMERCE_NEW_ORDER,
                fraudService::parse)) {
            kafkaService.run();
        }
    }

    private void parse(ConsumerRecord<String, String> record) {
        System.out.println("---------------------------------");
        System.out.println("Processing new order, checking for faud.");
        System.out.println(record.topic());
        System.out.println("key: " + record.key());
        System.out.println("value: " + record.value());
        System.out.println("offset: " + record.offset());
        System.out.println("partirion: " + record.partition());
        System.out.println(record.toString());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Order processed");
    }

}
