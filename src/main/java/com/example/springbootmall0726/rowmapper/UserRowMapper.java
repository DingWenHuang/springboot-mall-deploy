package com.example.springbootmall0726.rowmapper;

import com.example.springbootmall0726.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {

        // 建立UserRowMapper類別，實作RowMapper介面，並實作mapRow方法
        // mapRow方法是用來將資料庫查詢出來的資料轉換成User物件
        User user = new User();

        // 透過ResultSet物件取得資料庫查詢出來的資料，並將資料設定到User物件中
        user.setUserId(rs.getInt("user_id"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setCreatedDate(rs.getTimestamp("created_date"));
        user.setLastModifiedDate(rs.getTimestamp("last_modified_date"));

        return user;
    }
}
