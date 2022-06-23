package com.palmetto.model;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderId;

    @Column
    private String pizzaName;

    @Column
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    public OrderResponse toDto() {
        return OrderResponse.builder().pizzaName(pizzaName).orderId(orderId).build();
    }
}
