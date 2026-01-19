package com.Hussain.dreamshops.service.order;

import com.Hussain.dreamshops.dto.OrderDto;
import com.Hussain.dreamshops.model.Order;

import java.util.List;

public interface IOrderService {
    Order placeOrder(Long userId);
    OrderDto getOrder(Long orderId);
    List<OrderDto> getUserOrders(Long userId);
}