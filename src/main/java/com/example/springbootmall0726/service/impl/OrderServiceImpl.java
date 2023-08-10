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

        User user = userDao.getUserById(userId);

        if (user == null) {
            log.warn("User ID: {} is not exist", userId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        BigDecimal totalAmount = BigDecimal.ZERO;
        List<OrderItem> orderItemList = new ArrayList<>();

        for (BuyItem buyItem : createOrderRequest.getBuyItemList()) {
            Product product = productDao.getProductById(buyItem.getProductId());

            if (product == null) {
                log.warn("Product ID: {} is not exist", buyItem.getProductId());
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }

            if (product.getStock() < buyItem.getQuantity()) {
                log.warn("Stock of product ID: {} is insufficient! Stock is {}, wanted is {}", product.getProductId(), product.getStock(), buyItem.getQuantity());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

            Integer updatedStock = product.getStock() - buyItem.getQuantity();
            productDao.updateStock(product.getProductId(), updatedStock);

            BigDecimal amount = product.getPrice().multiply(BigDecimal.valueOf(buyItem.getQuantity()));
            totalAmount = totalAmount.add(amount);

            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(buyItem.getProductId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setAmount(amount);

            orderItemList.add(orderItem);
        }


        Integer orderId = orderDao.createOrder(userId, totalAmount);

        orderDao.createOrderItems(orderId, orderItemList);

        return orderId;
    }

    @Override
    public Order getOrderById(Integer orderId) {
        Order order = orderDao.getOrderById(orderId);

        List<OrderItem> orderItemList = orderDao.getOrderItemsByOrderId(orderId);

        order.setOrderItemList(orderItemList);

        return order;
    }

    @Override
    public List<Order> getOrders(GetOrdersParams getOrdersParams) {
        User user = userDao.getUserById(getOrdersParams.getUserId());

        if (user == null) {
            log.warn("User ID: {} is not exist", getOrdersParams.getUserId());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        List<Order> orderList = orderDao.getOrders(getOrdersParams);

        for (Order order : orderList) {

            List<OrderItem> orderItemList = orderDao.getOrderItemsByOrderId(order.getOrderId());

            order.setOrderItemList(orderItemList);
        }

        return orderList;
    }

    @Override
    public Integer countOrders(GetOrdersParams getOrdersParams) {
        return orderDao.countOrders(getOrdersParams);
    }
}
