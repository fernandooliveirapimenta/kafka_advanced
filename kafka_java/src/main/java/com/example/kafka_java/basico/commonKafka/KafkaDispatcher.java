package com.example.kafka_java.basico.commonKafka;

import lombok.var;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.Closeable;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import static com.example.kafka_java.Constants.*;
import static org.apache.kafka.clients.producer.ProducerConfig.*;

public class KafkaDispatcher<T> implements Closeable {

    private final KafkaProducer<String, T> producer;

    public KafkaDispatcher(){
        this.producer = new KafkaProducer<>(props());
    }

    private static Properties props() {

        var props = new Properties();

        props.setProperty(BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
        props.setProperty(KEY_SERIALIZER_CLASS_CONFIG, STRING_SERIALIZER);
        props.setProperty(VALUE_SERIALIZER_CLASS_CONFIG, JSON_SERIALIZER);
        return props;
    }

   public void send(String topic, String key, T value) {
        var record = new ProducerRecord<>(topic, key, value) ;
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

        try {
            producer.send(record, callback).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void close() {
        producer.close();
    }
}
