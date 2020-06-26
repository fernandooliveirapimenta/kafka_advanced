package com.example.kafka_java.basico;

import lombok.var;

import java.util.UUID;

import static com.example.kafka_java.Constants.*;

public class NewOrderMain {

    public static void main(String[] args) {

        try (var dispatcher = new KafkaDispatcher()) {
            for (int i = 0; i < 10; i++) {

                var value = "12345,3333,737737373737";
                var key = UUID.randomUUID().toString() + value;
                dispatcher.send(ECOMMERCE_NEW_ORDER, key, value);

                var email = "Welcome Thank estamos processando";
                dispatcher.send(ECOMMERCE_SEND_EMAIL, key, email);

            }
        }

    }

}
