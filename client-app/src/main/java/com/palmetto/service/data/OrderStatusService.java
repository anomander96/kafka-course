package com.palmetto.service.data;

import com.palmetto.model.OrderStatus;

public interface OrderStatusService {

    public void updateStatus(Long orderId, OrderStatus orderStatus);
}
