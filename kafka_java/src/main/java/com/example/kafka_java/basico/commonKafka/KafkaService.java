package com.example.kafka_java.basico.commonKafka;

import lombok.var;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.Closeable;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Pattern;

import static com.example.kafka_java.Constants.*;
import static java.util.Collections.singletonList;
import static org.apache.kafka.clients.consumer.ConsumerConfig.*;
import static org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG;

public class KafkaService<T> implements Closeable {

    private final ConsumerFunction parse;
    private final KafkaConsumer<String, T> consumer;

    private KafkaService(String groupId,
                 ConsumerFunction parse,
                         Class<T> type,
                         Map<String , String> config) {
        this.parse = parse;
        this.consumer = new KafkaConsumer<>(
                props(groupId, type, config));

    }

    public KafkaService(String groupId,
                 String topic,
                 ConsumerFunction parse,
                 Class<T> type) {

        this(groupId, parse, type, new HashMap<>());
        consumer.subscribe(singletonList(topic));

    }


    public KafkaService(String groupId,
                        Pattern topicPattern,
                        ConsumerFunction parse,
                        Class<T> type,
                        HashMap<String, String> conf) {
        this(groupId, parse, type, conf);
        consumer.subscribe(topicPattern);
    }


    public void run() {

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

    private Properties props(String groupId, Class<T> type, Map<String, String> config) {
        var props = new Properties();
        props.setProperty(BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
        props.setProperty(KEY_DESERIALIZER_CLASS_CONFIG, STRING_DESERIALIZER);
        props.setProperty(VALUE_DESERIALIZER_CLASS_CONFIG, JSON_DESERIALIZER);
        props.setProperty(GROUP_ID_CONFIG, groupId);
        props.setProperty(CLIENT_ID_CONFIG, UUID.randomUUID().toString());
        props.setProperty(MAX_POLL_RECORDS_CONFIG, "1");
        props.setProperty(GsonDeserializer.TYPE_CONIFG, type.getName());
        props.putAll(config);
        return props;
    }

    @Override
    public void close() {
        consumer.close();
    }
}
