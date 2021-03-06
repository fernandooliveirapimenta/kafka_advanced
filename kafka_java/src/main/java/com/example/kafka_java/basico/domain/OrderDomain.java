package com.example.kafka_java.basico.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderDomain {

    private final String orderId;
    private final BigDecimal amount;
    private final String email;


    public boolean isFraud(){
        return getAmount()
                .compareTo(new BigDecimal("4500")) >= 0;
    }
}
