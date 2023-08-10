package com.example.springbootmall0726.controller;

import com.example.springbootmall0726.dto.UserLoginRequest;
import com.example.springbootmall0726.dto.UserRegisterQuest;
import com.example.springbootmall0726.dto.UserUpdateRequest;
import com.example.springbootmall0726.model.User;
import com.example.springbootmall0726.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody @Valid UserRegisterQuest userRegisterQuest) {
        Integer userId = userService.register(userRegisterQuest);

        User user = userService.getUserById(userId);

        return ResponseEntity.status(201).body(user);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody @Valid UserLoginRequest userLoginRequest) {
        User user = userService.login(userLoginRequest);

        return ResponseEntity.status(200).body(user);
    }

    @PutMapping("/{userId}/update")
    public ResponseEntity<User> update(@PathVariable Integer userId,
                                       @RequestBody @Valid UserUpdateRequest userUpdateRequest) {
        userService.updateUser(userId, userUpdateRequest);

        User user = userService.getUserById(userId);

        return ResponseEntity.status(200).body(user);
    }
}
