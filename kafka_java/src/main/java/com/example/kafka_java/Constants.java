package com.example.kafka_java;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

public class Constants {

    public static final String BOOTSTRAP_SERVER = "localhost:9092";
    public static final String STRING_SERIALIZER = StringSerializer.class.getName();
    public static final String STRING_DESERIALIZER = StringDeserializer.class.getName();
    public static final String ECOMMERCE_NEW_ORDER = "ECOMMERCE_NEW_ORDER";
    public static final String ECOMMERCE_SEND_EMAIL = "ECOMMERCE_SEND_EMAIL";
}
