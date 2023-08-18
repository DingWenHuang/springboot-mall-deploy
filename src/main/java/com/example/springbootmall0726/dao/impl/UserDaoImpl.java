package com.example.springbootmall0726.dao.impl;

import com.example.springbootmall0726.dao.UserDao;
import com.example.springbootmall0726.dto.UserRegisterRequest;
import com.example.springbootmall0726.model.User;
import com.example.springbootmall0726.rowmapper.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer createUser(UserRegisterRequest userRegisterRequest) {

        // 創建SQL語句
        String sql = "INSERT INTO `user` (email, password, created_date, last_modified_date) VALUES (:email, :password, :createdDate, :lastModifiedDate)";

        // 創建Map對象用來設置SQL語句中的參數
        // 從userRegisterRequest中獲取email和password設置到map中
        Map<String, Object> map = new HashMap<>();
        map.put("email", userRegisterRequest.getEmail());
        map.put("password", userRegisterRequest.getPassword());

        // 取得當前時間用來設置SQL語句中的參數
        Date now = new Date();
        map.put("createdDate", now);
        map.put("lastModifiedDate", now);

        // 創建KeyHolder對象用來獲取創建的使用者的ID
        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        // 獲取創建的使用者的ID並回傳
        return keyHolder.getKey().intValue();
    }

    @Override
    public User getUserById(Integer userId) {

        // 創建SQL語句
        String sql  = "SELECT user_id, email, password, created_date, last_modified_date FROM `user` WHERE user_id = :userId";

        // 創建Map對象用來設置SQL語句中的參數，這裡是根據userId查詢，所以只需要設置userId這個參數
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);

        // 查詢使用者
        List<User> userList = namedParameterJdbcTemplate.query(sql, map, new UserRowMapper());

        // 如果查詢結果為空，則返回null，否則返回查詢結果的第一個元素
        if (userList.size() > 0) {
            return userList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public User getUserByEmail(String email) {

        // 創建SQL語句
        String sql  = "SELECT user_id, email, password, created_date, last_modified_date FROM `user` WHERE email = :email";

        // 創建Map對象用來設置SQL語句中的參數，這裡是根據email查詢，所以只需要設置email這個參數
        Map<String, Object> map = new HashMap<>();
        map.put("email", email);

        // 查詢使用者
        List<User> userList = namedParameterJdbcTemplate.query(sql, map, new UserRowMapper());

        // 如果查詢結果為空，則返回null，否則返回查詢結果的第一個元素
        if (userList.size() > 0) {
            return userList.get(0);
        } else {
            return null;
        }
    }
}
