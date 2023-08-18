package com.example.springbootmall0726.service;

import com.example.springbootmall0726.dto.UserLoginRequest;
import com.example.springbootmall0726.dto.UserRegisterRequest;
import com.example.springbootmall0726.model.User;

public interface UserService {

    Integer register(UserRegisterRequest userRegisterRequest);

    User getUserById(Integer userId);

    User login(UserLoginRequest userLoginRequest);
}
