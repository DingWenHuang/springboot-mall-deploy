package com.example.springbootmall0726.controller;

import com.example.springbootmall0726.dto.CreateOrderRequest;
import com.example.springbootmall0726.dto.GetOrdersParams;
import com.example.springbootmall0726.model.Order;
import com.example.springbootmall0726.service.OrderService;
import com.example.springbootmall0726.util.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "訂單功能", description = "提供使用者新增訂單，與查詢歷史訂單功能的API")
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Operation(summary = "提供使用者新增訂單，並同時會根據訂單內的商品品項更新商品庫存")
    @PostMapping("/users/{userId}/orders")
    public ResponseEntity<Order> createOrder(@PathVariable Integer userId,
                                         @RequestBody @Valid CreateOrderRequest createOrderRequest) {

        // 建立訂單，並取得建立後的訂單id
        Integer orderId = orderService.createOrder(userId, createOrderRequest);

        // 根據訂單id取得該筆訂單資料
        Order order = orderService.getOrderById(orderId);

        return ResponseEntity.status(201).body(order);
    }

    @Operation(summary = "提供使用者查詢歷史訂單，預設回傳五筆資料，並根據訂單建立日期降序排序")
    @GetMapping("/users/{userId}/orders")
    public ResponseEntity<Page<Order>> getOrders(@PathVariable Integer userId,
                                                 @RequestParam(defaultValue = "5") Integer limit,
                                                 @RequestParam(defaultValue = "0") Integer offset) {
        // 將查詢參數封裝成物件
        GetOrdersParams getOrdersParams = new GetOrdersParams();
        getOrdersParams.setUserId(userId);
        getOrdersParams.setLimit(limit);
        getOrdersParams.setOffset(offset);

        // 取得訂單列表
        List<Order> orderList = orderService.getOrders(getOrdersParams);

        // 取得訂單總筆數
        Integer total = orderService.countOrders(getOrdersParams);

        // 將訂單列表與訂單總筆數封裝成分頁物件
        Page<Order> orderPage = new Page<>();
        orderPage.setLimit(limit);
        orderPage.setOffset(offset);
        orderPage.setTotal(total);
        orderPage.setResults(orderList);

        return ResponseEntity.status(200).body(orderPage);
    }
}
