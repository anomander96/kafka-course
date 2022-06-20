package com.palmetto.service.data;

import com.palmetto.model.Order;

public interface OrderDataService {

    Order addOrder(Order order);

    Order getOrderById(long id);

    Order updateOrder(Order order);
}
