package com.palmetto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.palmetto.model.Order;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Order getOrderById(long orderId);
}
