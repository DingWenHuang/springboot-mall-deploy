package com.example.springbootmall0726.service.impl;

import com.example.springbootmall0726.dao.UserDao;
import com.example.springbootmall0726.dto.UserLoginRequest;
import com.example.springbootmall0726.dto.UserRegisterRequest;
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
    public Integer register(UserRegisterRequest userRegisterRequest) {

        // 根據 email 從資料庫中取得使用者資料
        User user = userDao.getUserByEmail(userRegisterRequest.getEmail());

        // 如果使用者資料不為 null，表示信箱已經被註冊過
        // 回傳 400 BAD_REQUEST，並且在 console 中印出警告訊息
        if (user != null) {
            log.warn("Email: {} has been registered", userRegisterRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // 將使用者輸入的密碼進行 MD5 雜湊處理
        // 將加密後的密碼設定回 userRegisterRequest
        String hashedPassword = DigestUtils.md5DigestAsHex(userRegisterRequest.getPassword().getBytes());
        userRegisterRequest.setPassword(hashedPassword);

        return userDao.createUser(userRegisterRequest);
    }

    @Override
    public User getUserById(Integer userId) {

        // 根據 userId 從資料庫中取得使用者資料
        return userDao.getUserById(userId);
    }

    @Override
    public User login(UserLoginRequest userLoginRequest) {

        // 根據 email 從資料庫中取得使用者資料
        User user = userDao.getUserByEmail(userLoginRequest.getEmail());

        // 如果使用者資料為 null，表示信箱尚未註冊過，資料庫內沒有該使用者資料
        if (user == null) {
            log.warn("Email: {} is wrong", userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // 將使用者輸入的密碼進行 MD5 雜湊處理
        String hashedPassword = DigestUtils.md5DigestAsHex(userLoginRequest.getPassword().getBytes());

        // 比對資料庫中的密碼與使用者輸入的密碼是否相同
        if (!hashedPassword.equals(user.getPassword())) {
            log.warn("Password for Email: {} is wrong", userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return user;
    }
}
