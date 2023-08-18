package com.example.springbootmall0726.util;

import com.example.springbootmall0726.model.Product;

import java.util.List;

public class Page<T> {

    // Page是用來接收資料庫查詢出來的分頁資料，包含目前頁數、總頁數、查詢結果
    // 查詢結果是由T物件組成的List，T物件可以是自定義物件，例如：Product、Order等等

    private Integer limit;
    private Integer offset;
    private Integer total;
    private List<T> results;

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

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }
}
