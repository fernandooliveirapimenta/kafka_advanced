package com.example.kafka_java.basico;

import lombok.var;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Properties;
import java.util.regex.Pattern;

import static com.example.kafka_java.Constants.*;
import static java.util.Collections.singletonList;
import static org.apache.kafka.clients.consumer.ConsumerConfig.*;

public class LogServiceMain {

    public static void main(String[] args) throws InterruptedException {
        var consumer = new KafkaConsumer<>(props());

        consumer.subscribe(Pattern.compile("ECOMMERCE.*"));

        while (true) {

            var records = consumer.poll(Duration.ofMillis(100));

            if(!records.isEmpty()) {
                System.out.println("Encontrei " + records.count() + " registros");

                for(var record: records){
                    System.out.println("---------------------------------");
                    System.out.println("Sending email");
                    System.out.println(record.topic());
                    System.out.println("key: " + record.key());
                    System.out.println("value: " + record.value());
                    System.out.println("offset: " + record.offset());
                    System.out.println("partirion: " + record.partition());
                    System.out.println(record.toString());
                    Thread.sleep(1000);
                    System.out.println("Send email sucess");
                }
            }

        }
    }

    private static Properties props() {
        var props = new Properties();
        props.setProperty(BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
        props.setProperty(KEY_DESERIALIZER_CLASS_CONFIG, STRING_DESERIALIZER);
        props.setProperty(VALUE_DESERIALIZER_CLASS_CONFIG, STRING_DESERIALIZER);
        props.setProperty(GROUP_ID_CONFIG, LogServiceMain.class.getSimpleName());
        return props;
    }
}
