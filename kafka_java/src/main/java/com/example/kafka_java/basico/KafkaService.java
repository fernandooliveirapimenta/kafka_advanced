package com.example.kafka_java.basico;

import lombok.var;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.Closeable;
import java.time.Duration;
import java.util.Properties;
import java.util.UUID;

import static com.example.kafka_java.Constants.*;
import static java.util.Collections.singletonList;
import static org.apache.kafka.clients.consumer.ConsumerConfig.*;
import static org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG;

class KafkaService implements Closeable {

    private final ConsumerFunction parse;
    private final KafkaConsumer<String, String> consumer;

    KafkaService(String groupId,
                 String topic,
                 ConsumerFunction parse) {
        this.parse = parse;
        this.consumer = new KafkaConsumer<>(props(groupId));
        consumer.subscribe(singletonList(topic));

    }


    void run() {

        while (true) {
            var records = consumer.poll(Duration.ofMillis(100));
            if(!records.isEmpty()) {
                System.out.println("Encontrei " + records.count() + " registros");
                for(var record: records){
                    parse.consume(record);
                }
            }
        }
    }

    private Properties props(String groupId) {
        var props = new Properties();
        props.setProperty(BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
        props.setProperty(KEY_DESERIALIZER_CLASS_CONFIG, STRING_DESERIALIZER);
        props.setProperty(VALUE_DESERIALIZER_CLASS_CONFIG, STRING_DESERIALIZER);
        props.setProperty(GROUP_ID_CONFIG, groupId);
        props.setProperty(CLIENT_ID_CONFIG, UUID.randomUUID().toString());
        props.setProperty(MAX_POLL_RECORDS_CONFIG, "1");
        return props;
    }

    @Override
    public void close() {
        consumer.close();
    }
}
