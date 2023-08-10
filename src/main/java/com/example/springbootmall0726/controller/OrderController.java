package com.example.springbootmall0726.controller;

import com.example.springbootmall0726.dto.CreateOrderRequest;
import com.example.springbootmall0726.dto.GetOrdersParams;
import com.example.springbootmall0726.model.Order;
import com.example.springbootmall0726.service.OrderService;
import com.example.springbootmall0726.util.Page;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/users/{userId}/orders")
    public ResponseEntity<Order> createOrder(@PathVariable Integer userId,
                                         @RequestBody @Valid CreateOrderRequest createOrderRequest) {
        Integer orderId = orderService.createOrder(userId, createOrderRequest);

        Order order = orderService.getOrderById(orderId);

        return ResponseEntity.status(201).body(order);
    }

    @GetMapping("/users/{userId}/orders")
    public ResponseEntity<Page<Order>> getOrders(@PathVariable Integer userId,
                                                 @RequestParam(defaultValue = "5") Integer limit,
                                                 @RequestParam(defaultValue = "0") Integer offset) {
        GetOrdersParams getOrdersParams = new GetOrdersParams();
        getOrdersParams.setUserId(userId);
        getOrdersParams.setLimit(limit);
        getOrdersParams.setOffset(offset);

        List<Order> orderList = orderService.getOrders(getOrdersParams);

        Integer total = orderService.countOrders(getOrdersParams);

        Page<Order> orderPage = new Page<>();
        orderPage.setLimit(limit);
        orderPage.setOffset(offset);
        orderPage.setTotal(total);
        orderPage.setResults(orderList);

        return ResponseEntity.status(200).body(orderPage);
    }
}
