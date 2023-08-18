package com.example.springbootmall0726.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Order {

    // Order是用來接收資料庫查詢出來的訂單資料，包含訂單id、使用者id、訂單總金額、訂單建立時間、訂單最後修改時間、訂單內容

    private Integer orderId;
    private Integer userId;
    private BigDecimal totalAmount;
    private Date createdDate;
    private Date lastModifiedDate;

    // 訂單內容是由OrderItem物件組成的List，OrderItem物件需要提供商品id、購買數量、商品小計
    private List<OrderItem> orderItemList;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }
}
