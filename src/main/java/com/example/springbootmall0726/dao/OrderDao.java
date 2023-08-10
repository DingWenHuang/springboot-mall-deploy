package com.example.springbootmall0726.dao;

import com.example.springbootmall0726.dto.CreateOrderRequest;
import com.example.springbootmall0726.dto.GetOrdersParams;
import com.example.springbootmall0726.model.Order;
import com.example.springbootmall0726.model.OrderItem;

import java.math.BigDecimal;
import java.util.List;

public interface OrderDao {

    Integer createOrder(Integer userId, BigDecimal totalAmount);

    Order getOrderById(Integer orderId);

    List<OrderItem> getOrderItemsByOrderId(Integer orderId);

    void createOrderItems(Integer orderId, List<OrderItem> orderItemList);

    List<Order> getOrders(GetOrdersParams getOrdersParams);

    Integer countOrders(GetOrdersParams getOrdersParams);
}
