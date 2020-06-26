package com.example.kafka_java.basico;

import com.example.kafka_java.basico.commonKafka.KafkaService;
import lombok.var;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.HashMap;
import java.util.regex.Pattern;

public class LogServiceMain {

    public static void main(String[] args)  {
        var logService = new LogServiceMain();

       var conf = new HashMap<String, String>();
       conf.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        try (var service = new KafkaService<String>(
                LogServiceMain.class.getSimpleName(),
                Pattern.compile("ECOMMERCE.*"),
             logService::parse,
                String.class,
                conf )) {
            service.run();
        }
    }

    private void parse(ConsumerRecord<String, String> record) {
        System.out.println("---------------------------------");
        System.out.println("Sending email");
        System.out.println(record.topic());
        System.out.println("key: " + record.key());
        System.out.println("value: " + record.value());
        System.out.println("offset: " + record.offset());
        System.out.println("partirion: " + record.partition());
        System.out.println(record.toString());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Send email sucess");
    }

}
