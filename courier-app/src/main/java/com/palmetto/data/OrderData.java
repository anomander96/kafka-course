package com.palmetto.data;

import com.palmetto.model.OrderStatus;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class OrderData {

    private Long orderId;
    private String correlationId;
    private String pizzaName;
    private OrderStatus status;
}
