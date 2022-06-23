package com.palmetto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.palmetto.model.Order;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> getOrderById(Long orderId);

    Optional<Order> getStatusByOrderId(Long orderId);
}
