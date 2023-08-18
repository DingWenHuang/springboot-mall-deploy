package com.example.springbootmall0726.rowmapper;

import com.example.springbootmall0726.model.OrderItem;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderItemRowMapper implements RowMapper<OrderItem> {
    @Override
    public OrderItem mapRow(ResultSet rs, int rowNum) throws SQLException {

        // 建立OrderItemRowMapper類別，實作RowMapper介面，並實作mapRow方法
        // mapRow方法是用來將資料庫查詢出來的資料轉換成OrderItem物件
        OrderItem orderItem = new OrderItem();

        // 透過ResultSet物件取得資料庫查詢出來的資料，並將資料設定到OrderItem物件中
        orderItem.setOrderItemId(rs.getInt("order_item_id"));
        orderItem.setOrderId(rs.getInt("order_id"));
        orderItem.setProductId(rs.getInt("product_id"));
        orderItem.setQuantity(rs.getInt("quantity"));
        orderItem.setAmount(BigDecimal.valueOf(rs.getInt("amount")));

        // OrderItem中的productName和imageUrl是從商品資料庫查詢出來的，為了提供前端顯示訂單內容時使用
        orderItem.setProductName(rs.getString("product_name"));
        orderItem.setImageUrl(rs.getString("image_url"));

        return orderItem;
    }
}
