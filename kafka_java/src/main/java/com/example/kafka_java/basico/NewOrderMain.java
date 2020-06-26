package com.example.kafka_java.basico;

import lombok.var;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import static com.example.kafka_java.Constants.*;
import static org.apache.kafka.clients.producer.ProducerConfig.*;

public class NewOrderMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
      var producer = new KafkaProducer<String, String>(props());

      for (int i=0; i<10; i++) {

          var value = "12345,3333,737737373737";
          var key = UUID.randomUUID().toString() + value;
          var recordOrder = new ProducerRecord<>(ECOMMERCE_NEW_ORDER, key, value) ;
          final Callback callback = (data, err) -> {
              if (err != null) {
                  err.printStackTrace();
                  return;
              }
              System.out.println("sucesso enviando " + data.topic()
                      + ":::partition " + data.partition()
                      + "/ offset " + data.offset()
                      + "/ " + data.timestamp());
          };

          var email = "Welcome Thank estamos processando";
          producer.send(recordOrder, callback).get();
          var recordEmail = new ProducerRecord<>(ECOMMERCE_SEND_EMAIL, key, email) ;
          producer.send(recordEmail, callback).get();

      }
    }

    private static Properties props() {

        var props = new Properties();

        props.setProperty(BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
        props.setProperty(KEY_SERIALIZER_CLASS_CONFIG, STRING_SERIALIZER);
        props.setProperty(VALUE_SERIALIZER_CLASS_CONFIG, STRING_SERIALIZER);
        return props;
    }
}
