package org.fernando.meu;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDomain {

    private String orderId;
    private BigDecimal amount;
    private String email;

}
