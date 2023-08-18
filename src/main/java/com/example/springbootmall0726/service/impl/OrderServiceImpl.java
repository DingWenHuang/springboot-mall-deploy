package com.example.springbootmall0726.service.impl;

import com.example.springbootmall0726.dao.OrderDao;
import com.example.springbootmall0726.dao.ProductDao;
import com.example.springbootmall0726.dao.UserDao;
import com.example.springbootmall0726.dto.BuyItem;
import com.example.springbootmall0726.dto.CreateOrderRequest;
import com.example.springbootmall0726.dto.GetOrdersParams;
import com.example.springbootmall0726.model.Order;
import com.example.springbootmall0726.model.OrderItem;
import com.example.springbootmall0726.model.Product;
import com.example.springbootmall0726.model.User;
import com.example.springbootmall0726.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserDao userDao;

    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Override
    @Transactional
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {

        // 創建訂單前，先確認使用者是否存在
        User user = userDao.getUserById(userId);

        // 如果使用者不存在，則回傳 404 NOT_FOUND，並且在 console 中印出警告訊息
        if (user == null) {
            log.warn("User ID: {} is not exist", userId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        // 從 createOrderRequest 中取得 buyItemList
        // 建立一個 orderItemList 來存放訂單項目
        List<BuyItem> buyItemList = createOrderRequest.getBuyItemList();
        List<OrderItem> orderItemList = new ArrayList<>();

        // 建立一個 BigDecimal 來存放訂單總金額
        BigDecimal totalAmount = new BigDecimal(0);

        // 逐一檢查 buyItemList 中的每一個 buyItem
        for (BuyItem buyItem : buyItemList) {

            // 檢查 buyItem 中的 productId 是否存在
            Product product = productDao.getProductById(buyItem.getProductId());

            // 如果商品資料不存在，則回傳 404 NOT_FOUND，並且在 console 中印出警告訊息
            if (product == null) {
                log.warn("Product ID: {} is not exist", buyItem.getProductId());
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }

            // 檢查商品庫存是否足夠
            // 如果大於庫存，則回傳 400 BAD_REQUEST，並且在 console 中印出警告訊息
            if (product.getStock() < buyItem.getQuantity()) {
                log.warn("Stock of product ID: {} is insufficient! Stock is {}, wanted is {}", product.getProductId(), product.getStock(), buyItem.getQuantity());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

            // 更新商品庫存
            Integer updatedStock = product.getStock() - buyItem.getQuantity();
            productDao.updateStock(product.getProductId(), updatedStock);

            // 計算訂單項目的金額
            BigDecimal amount = product.getPrice().multiply(BigDecimal.valueOf(buyItem.getQuantity()));

            // 計算訂單總金額
            totalAmount = totalAmount.add(amount);

            // 建立訂單項目
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(buyItem.getProductId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setAmount(amount);

            // 將訂單項目加入 orderItemList
            orderItemList.add(orderItem);
        }

        // 建立訂單，並取得 orderId
        Integer orderId = orderDao.createOrder(userId, totalAmount);

        // 根據 orderId 與 orderItemList 建立訂單項目
        orderDao.createOrderItems(orderId, orderItemList);

        // 回傳 orderId
        return orderId;
    }

    @Override
    public Order getOrderById(Integer orderId) {

        // 根據 orderId 取得訂單
        Order order = orderDao.getOrderById(orderId);

        // 根據 orderId 取得訂單項目
        List<OrderItem> orderItemList = orderDao.getOrderItemsByOrderId(orderId);

        // 將訂單項目加入訂單後再回傳
        order.setOrderItemList(orderItemList);

        return order;
    }

    @Override
    public List<Order> getOrders(GetOrdersParams getOrdersParams) {

        // 先根據orderQueryParams中的userId取得使用者
        User user = userDao.getUserById(getOrdersParams.getUserId());

        // 如果使用者不存在，則回傳 404 NOT_FOUND，並且在 console 中印出警告訊息
        if (user == null) {
            log.warn("User ID: {} is not exist", getOrdersParams.getUserId());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        // 根據 orderQueryParams 取得符合該條件的訂單
        List<Order> orderList = orderDao.getOrders(getOrdersParams);

        // 取得orderList中每一筆訂單，並根據訂單id取得訂單項目
        // 將訂單項目加入訂單後再回傳
        for (Order order : orderList) {
            List<OrderItem> orderItemList = orderDao.getOrderItemsByOrderId(order.getOrderId());
            order.setOrderItemList(orderItemList);
        }

        return orderList;
    }

    @Override
    public Integer countOrders(GetOrdersParams getOrdersParams) {

        // 根據 orderQueryParams 取得該條件下訂單數量
        return orderDao.countOrders(getOrdersParams);
    }
}
