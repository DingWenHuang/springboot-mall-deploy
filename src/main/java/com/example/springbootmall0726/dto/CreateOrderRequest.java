package com.example.springbootmall0726.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class CreateOrderRequest {

    // CreateOrderRequest是用來接收前端傳來的資料，需要提供購買商品的清單
    // 商品清單是由BuyItem物件組成的List，BuyItem物件需要提供商品id和購買數量

    @NotEmpty
    @Valid
    private List<BuyItem> buyItemList;

    public List<BuyItem> getBuyItemList() {
        return buyItemList;
    }

    public void setBuyItemList(List<BuyItem> buyItemList) {
        this.buyItemList = buyItemList;
    }
}
