package com.example.kafka_java.basico;

import com.example.kafka_java.basico.commonKafka.KafkaDispatcher;
import com.example.kafka_java.basico.domain.OrderDomain;
import lombok.var;

import java.math.BigDecimal;
import java.util.UUID;

import static com.example.kafka_java.Constants.*;

public class NewOrderMain {

    public static void main(String[] args) {

        try (var orderDispatcher = new KafkaDispatcher<OrderDomain>()) {
            try (var emailDispatcher = new KafkaDispatcher<String>()) {

                for (int i = 0; i < 3; i++) {

//                    var userId = UUID.randomUUID().toString();
                    var orderId = UUID.randomUUID().toString();
                    var amount = new BigDecimal(Math.random() * 5000 +  1);
                    var email = Math.random() + "fosilva@bbmapfre.com.br";
//                    var email = "fosilva@bbmapfre.com.br";
                    var order = new OrderDomain(orderId, amount,email);


                    var value = "12345,3333,737737373737";
                    var key = UUID.randomUUID().toString() + value;
                    orderDispatcher.send(ECOMMERCE_NEW_ORDER, email, order);

                    var email2 = "Welcome Thank estamos processando";
                    emailDispatcher.send(ECOMMERCE_SEND_EMAIL, key, email2);

                }
            }
        }

    }

}
