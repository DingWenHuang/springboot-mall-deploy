package com.example.springbootmall0726.controller;

import com.example.springbootmall0726.dto.UserLoginRequest;
import com.example.springbootmall0726.dto.UserRegisterRequest;
import com.example.springbootmall0726.model.User;
import com.example.springbootmall0726.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "使用者功能", description = "提供使用者註冊、登入的API")
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "將使用者資料註冊到資料庫內，並回傳儲存後的結果，如被註冊過會400 Bad Request")
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody @Valid UserRegisterRequest userRegisterRequest) {

        // 建立使用者，並取得建立後的使用者id
        Integer userId = userService.register(userRegisterRequest);

        // 根據使用者id取得該筆使用者資料
        User user = userService.getUserById(userId);

        return ResponseEntity.status(201).body(user);
    }

    @Operation(summary = "以使用者提供的資料做登入，如資料有錯誤會回傳400 Bad Request")
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody @Valid UserLoginRequest userLoginRequest) {

        // 登入使用者，並取得登入後的使用者資料
        User user = userService.login(userLoginRequest);

        return ResponseEntity.status(200).body(user);
    }
}
