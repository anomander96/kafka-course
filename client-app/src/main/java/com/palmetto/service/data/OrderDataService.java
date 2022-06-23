package com.palmetto.service.data;

import com.palmetto.model.Order;
import com.palmetto.model.OrderRequest;
import com.palmetto.model.OrderResponse;

public interface OrderDataService {

    public OrderResponse process(OrderRequest orderRequest);
}
