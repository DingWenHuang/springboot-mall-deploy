package com.example.springbootmall0726.dao;

import com.example.springbootmall0726.dto.UserLoginRequest;
import com.example.springbootmall0726.dto.UserRegisterQuest;
import com.example.springbootmall0726.dto.UserUpdateRequest;
import com.example.springbootmall0726.model.User;

public interface UserDao {

    Integer createUser(UserRegisterQuest userRegisterQuest);

    User getUserById(Integer userId);

    User getUserByEmail(String email);

    void updateUserData(Integer userId, UserUpdateRequest userUpdateRequest);

    void updateUserPassword(Integer userId, UserUpdateRequest userUpdateRequest);
}
