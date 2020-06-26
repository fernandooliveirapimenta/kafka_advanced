package com.example.kafka_java.basico.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderDomain {

    private final String userId;
    private final String orderId;
    private final BigDecimal amount;
}
