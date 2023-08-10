package com.example.springbootmall0726.service.impl;

import com.example.springbootmall0726.dao.UserDao;
import com.example.springbootmall0726.dto.UserLoginRequest;
import com.example.springbootmall0726.dto.UserRegisterQuest;
import com.example.springbootmall0726.dto.UserUpdateRequest;
import com.example.springbootmall0726.model.User;
import com.example.springbootmall0726.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    @Override
    public Integer register(UserRegisterQuest userRegisterQuest) {

        User user = userDao.getUserByEmail(userRegisterQuest.getEmail());

        if (user != null) {
            log.warn("Email: {} has been registered", userRegisterQuest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        String hashedPassword = DigestUtils.md5DigestAsHex(userRegisterQuest.getPassword().getBytes());
        userRegisterQuest.setPassword(hashedPassword);

        return userDao.createUser(userRegisterQuest);
    }

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public User login(UserLoginRequest userLoginRequest) {

        User user = userDao.getUserByEmail(userLoginRequest.getEmail());

        if (user == null) {
            log.warn("Email: {} is wrong", userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        String hashedPassword = DigestUtils.md5DigestAsHex(userLoginRequest.getPassword().getBytes());
        if (!hashedPassword.equals(user.getPassword())) {
            log.warn("Password for Email: {} is wrong", userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return user;
    }

    @Override
    public void updateUser(Integer userId, UserUpdateRequest userUpdateRequest) {
        User user = userDao.getUserById(userId);

        if (user == null) {
            log.warn("User ID: {} not found", userId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        String hashedPassword = DigestUtils.md5DigestAsHex(userUpdateRequest.getPassword().getBytes());
        if (!hashedPassword.equals(user.getPassword())) {
            log.warn("Password for Email: {} is wrong", userUpdateRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (!userUpdateRequest.getEmail().equals(user.getEmail())) {
            User resiteredUser = userDao.getUserByEmail(userUpdateRequest.getEmail());

            if (resiteredUser != null) {
                log.warn("Email: {} has been registered", userUpdateRequest.getEmail());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

            userDao.updateUserData(userId, userUpdateRequest);
        }

        if (userUpdateRequest.getNewPassword() != null) {
            String hashedNewPassword = DigestUtils.md5DigestAsHex(userUpdateRequest.getNewPassword().getBytes());
            userUpdateRequest.setNewPassword(hashedNewPassword);

            userDao.updateUserPassword(userId, userUpdateRequest);
        }
    }
}
