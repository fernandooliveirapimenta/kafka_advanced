package com.example.kafka_java.basico;

import com.example.kafka_java.basico.commonKafka.KafkaDispatcher;
import com.example.kafka_java.basico.commonKafka.KafkaService;
import com.example.kafka_java.basico.domain.OrderDomain;
import lombok.var;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import static com.example.kafka_java.Constants.*;

public class FraudDetectorServiceMain {

    private final KafkaDispatcher<OrderDomain> dispatcher
            = new KafkaDispatcher<>();

    public static void main(String[] args) {
        var fraudService = new FraudDetectorServiceMain();
        try (var kafkaService = new KafkaService<>
                (FraudDetectorServiceMain.class.getSimpleName(),
                ECOMMERCE_NEW_ORDER,
                fraudService::parse,
                        OrderDomain.class)) {
            kafkaService.run();
        }
    }

    private void parse(ConsumerRecord<String, OrderDomain> record) {
        System.out.println("---------------------------------");
        System.out.println(record.topic());
//        System.out.println("key: " + record.key());
        System.out.println("value: " + record.value());
        System.out.println("offset: " + record.offset());
//        System.out.println("partirion: " + record.partition());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        final OrderDomain order = record.value();

        if(order.isFraud()){
            System.out.println("Reprovada: " + order);
            dispatcher.send(ECOMMERCE_ORDER_REJECTED,
                    order.getEmail(),
                    order );
        } else {
            dispatcher.send(ECOMMERCE_ORDER_APROVED,
                    order.getEmail(),
                    order );
            System.out.println("Aprovada: " + order);
        }
    }

}
