package com.example.kafka_java.basico;

import lombok.var;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import static com.example.kafka_java.Constants.*;

public class EmailServiceMain {

    public static void main(String[] args)  {
       var emailService = new EmailServiceMain();
       try (var service = new KafkaService(EmailServiceMain.class.getSimpleName(),
               ECOMMERCE_SEND_EMAIL,
               emailService::parse )) {
           service.run();
       }

    }

    private void parse(ConsumerRecord<String, String> record){

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
