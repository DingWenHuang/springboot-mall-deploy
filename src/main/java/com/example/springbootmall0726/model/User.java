package com.example.springbootmall0726.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

public class User {

    // User是用來接收資料庫查詢出來的使用者資料，包含使用者id、使用者email、使用者密碼、使用者建立時間、使用者最後修改時間
    // 使用者密碼加上JsonIgnore註解是為了安全性，不會回傳給前端

    private Integer userId;
    private String email;

    @JsonIgnore
    private String password;
    private Date createdDate;
    private Date lastModifiedDate;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}
