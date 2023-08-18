package com.example.springbootmall0726.rowmapper;

import com.example.springbootmall0726.model.Order;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderRowMapper implements RowMapper<Order> {
    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {

        // 建立OrderRowMapper類別，實作RowMapper介面，並實作mapRow方法
        // mapRow方法是用來將資料庫查詢出來的資料轉換成Order物件
        Order order = new Order();

        // 透過ResultSet物件取得資料庫查詢出來的資料，並將資料設定到Order物件中
        order.setOrderId(rs.getInt("order_id"));
        order.setUserId(rs.getInt("user_id"));
        order.setTotalAmount(BigDecimal.valueOf(rs.getInt("total_amount")));
        order.setCreatedDate(rs.getTimestamp("created_date"));
        order.setLastModifiedDate(rs.getTimestamp("last_modified_date"));

        return order;
    }
}
