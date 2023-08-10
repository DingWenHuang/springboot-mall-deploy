package com.example.springbootmall0726.controller;

import com.example.springbootmall0726.dto.UserLoginRequest;
import com.example.springbootmall0726.dto.UserRegisterQuest;
import com.example.springbootmall0726.dto.UserUpdateRequest;
import com.example.springbootmall0726.model.User;
import com.example.springbootmall0726.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;


@Tag(name = "使用者功能", description = "提供使用者註冊、登入、更新資料的API")
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "將使用者資料註冊到資料庫內，並回傳儲存後的結果，如被註冊過會400 Bad Request")
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody @Valid UserRegisterQuest userRegisterQuest) {
        Integer userId = userService.register(userRegisterQuest);

        User user = userService.getUserById(userId);

        return ResponseEntity.status(201).body(user);
    }

    @Operation(summary = "以使用者提供的資料做登入，如資料有錯誤會回傳400 Bad Request")
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody @Valid UserLoginRequest userLoginRequest) {
        User user = userService.login(userLoginRequest);

        return ResponseEntity.status(200).body(user);
    }

    @Operation(summary = "將使用者提供的資料更新到資料庫內，並回傳更新後的結果，如資料有錯誤會回傳400 Bad Request")
    @PutMapping("/{userId}/update")
    public ResponseEntity<User> update(@PathVariable Integer userId,
                                       @RequestBody @Valid UserUpdateRequest userUpdateRequest) {
        userService.updateUser(userId, userUpdateRequest);

        User user = userService.getUserById(userId);

        return ResponseEntity.status(200).body(user);
    }
}
