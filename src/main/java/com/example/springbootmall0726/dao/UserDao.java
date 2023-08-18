package com.example.springbootmall0726.dao;

import com.example.springbootmall0726.dto.UserRegisterRequest;
import com.example.springbootmall0726.model.User;

public interface UserDao {

    Integer createUser(UserRegisterRequest userRegisterRequest);

    User getUserById(Integer userId);

    User getUserByEmail(String email);
}
