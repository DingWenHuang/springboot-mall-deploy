package com.example.springbootmall0726.dto;

import com.example.springbootmall0726.constant.ProductCategory;

public class GetProductsParams {

    // GetProductsParams是用來接收前端傳來的資料，用來查詢商品
    // 需要提供搜尋字串、商品類別、排序欄位、排序方式、資料數量、跳過幾筆資料
    // 若無提供則使用ProductController中的預設值

    private String search;
    private ProductCategory category;
    private String orderBy;
    private String sort;
    private Integer limit;
    private Integer offset;

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public Integer getLimit() {
        return limit;
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
