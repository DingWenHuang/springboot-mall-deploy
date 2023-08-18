package com.example.springbootmall0726.dto;

import com.example.springbootmall0726.constant.ProductCategory;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class ProductRequest {

    // ProductRequest是用來接收前端傳來的資料，用來新增商品
    // 需要提供商品名稱、商品類別、商品圖片、商品價格、商品庫存、商品描述
    // 商品類別是ProductCategory列舉類別，包含FOOD,CAR,BOOK三種類別
    // 商品價格是BigDecimal類別，用來處理金額的計算，避免使用double或float
    // description可以為空字串，但其他欄位都不可以為空

    @NotNull
    private String productName;

    @NotNull
    private ProductCategory category;

    @NotNull
    private String imageUrl;

    @NotNull
    private BigDecimal price;

    @NotNull
    private Integer stock;

    private String description;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
