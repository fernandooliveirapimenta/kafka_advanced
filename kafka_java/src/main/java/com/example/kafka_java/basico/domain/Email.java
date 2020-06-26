package com.example.kafka_java.basico.domain;

import lombok.Data;

@Data
public class Email {
    private final String subject;
    private final String body;
}
