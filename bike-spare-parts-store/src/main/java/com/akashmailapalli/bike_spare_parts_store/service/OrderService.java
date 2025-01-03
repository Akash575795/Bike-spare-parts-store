package com.akashmailapalli.bike_spare_parts_store.service;

import java.util.List;

import com.akashmailapalli.bike_spare_parts_store.dto.OrderDTO;

public interface OrderService {

    OrderDTO createOrder(OrderDTO orderDTO);
    OrderDTO updateOrderStatus(Long orderId, String status);
    List<OrderDTO> getOrdersByUserId(Long userId);
}
