package com.example.springbootmall0726.service;

import com.example.springbootmall0726.dto.UserLoginRequest;
import com.example.springbootmall0726.dto.UserRegisterQuest;
import com.example.springbootmall0726.dto.UserUpdateRequest;
import com.example.springbootmall0726.model.User;

public interface UserService {

    Integer register(UserRegisterQuest userRegisterQuest);

    User getUserById(Integer userId);

    User login(UserLoginRequest userLoginRequest);

    void updateUser(Integer userId, UserUpdateRequest userUpdateRequest);
}
