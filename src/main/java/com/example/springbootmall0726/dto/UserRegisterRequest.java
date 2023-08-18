package com.example.springbootmall0726.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserRegisterRequest {

    // UserRegisterRequest是用來接收前端傳來的資料，用來註冊
    // 需要提供使用者email和密碼
    // 與UserLoginRequest目前雖然都只需要email和密碼，但未來可能會有不同的需求
    // 例如註冊時提供姓名、性別等資料，或是登入時提供手機號碼、生日等資料


    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
