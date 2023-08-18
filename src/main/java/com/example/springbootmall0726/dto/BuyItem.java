package com.example.springbootmall0726.dto;

import jakarta.validation.constraints.NotNull;

public class BuyItem {

    // BuyItem表示訂單內的購買項目，需要提供商品id和購買數量

    @NotNull
    private Integer productId;

    @NotNull
    private Integer quantity;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
