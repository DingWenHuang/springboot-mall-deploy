package com.example.springbootmall0726.dto;

public class GetOrdersParams {

    // OrderQueryParams是用來接收前端傳來的資料，用來查詢訂單
    // 需要提供使用者id、資料數量、跳過幾筆資料

    private Integer userId;

    private Integer limit;

    private Integer offset;

    public Integer getLimit() {
        return limit;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }
}
