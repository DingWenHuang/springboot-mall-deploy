package com.example.springbootmall0726.dao.impl;

import com.example.springbootmall0726.dao.UserDao;
import com.example.springbootmall0726.dto.UserLoginRequest;
import com.example.springbootmall0726.dto.UserRegisterQuest;
import com.example.springbootmall0726.dto.UserUpdateRequest;
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
    public Integer createUser(UserRegisterQuest userRegisterQuest) {
        String sql = "INSERT INTO `user` (email, password, created_date, last_modified_date) VALUES (:email, :password, :createdDate, :lastModifiedDate)";

        Map<String, Object> map = new HashMap<>();
        map.put("email", userRegisterQuest.getEmail());
        map.put("password", userRegisterQuest.getPassword());

        Date now = new Date();
        map.put("createdDate", now);
        map.put("lastModifiedDate", now);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public User getUserById(Integer userId) {
        String sql  = "SELECT user_id, email, password, created_date, last_modified_date FROM `user` WHERE user_id = :userId";

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);

        List<User> userList = namedParameterJdbcTemplate.query(sql, map, new UserRowMapper());

        if (userList.size() > 0) {
            return userList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public User getUserByEmail(String email) {
        String sql  = "SELECT user_id, email, password, created_date, last_modified_date FROM `user` WHERE email = :email";

        Map<String, Object> map = new HashMap<>();
        map.put("email", email);

        List<User> userList = namedParameterJdbcTemplate.query(sql, map, new UserRowMapper());

        if (userList.size() > 0) {
            return userList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public void updateUserData(Integer userId, UserUpdateRequest userUpdateRequest) {
        String sql = "UPDATE `user` SET email = :newEmail, last_modified_date = :lastModifiedDate WHERE user_id = :userId";

        Map<String, Object> map = new HashMap<>();
        map.put("newEmail", userUpdateRequest.getEmail());
        map.put("userId", userId);
        map.put("lastModifiedDate", new Date());

        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public void updateUserPassword(Integer userId, UserUpdateRequest userUpdateRequest) {
        String sql = "UPDATE `user` SET password = :newPassword, last_modified_date = :lastModifiedDate WHERE user_id = :userId";

        Map<String, Object> map = new HashMap<>();
        map.put("newPassword", userUpdateRequest.getNewPassword());
        map.put("userId", userId);
        map.put("lastModifiedDate", new Date());

        namedParameterJdbcTemplate.update(sql, map);
    }
}
