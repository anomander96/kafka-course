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
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderId;

    @Column
    private Timestamp orderTime;

    @Column
    private String details;

    @Column
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

}
