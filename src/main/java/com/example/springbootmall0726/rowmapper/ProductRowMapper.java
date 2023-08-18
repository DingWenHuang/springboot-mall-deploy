package com.example.springbootmall0726.rowmapper;

import com.example.springbootmall0726.constant.ProductCategory;
import com.example.springbootmall0726.model.Product;
import com.example.springbootmall0726.service.ProductService;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRowMapper implements RowMapper<Product> {
    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {

        // 建立OrderRowMapper類別，實作RowMapper介面，並實作mapRow方法
        // mapRow方法是用來將資料庫查詢出來的資料轉換成Product物件
        Product product = new Product();

        // 透過ResultSet物件取得資料庫查詢出來的資料，並將資料設定到Product物件中
        product.setProductId(rs.getInt("product_id"));
        product.setProductName(rs.getString("product_name"));
        product.setCategory(ProductCategory.valueOf(rs.getString("category")));
        product.setImageUrl(rs.getString("image_url"));
        product.setPrice(rs.getBigDecimal("price"));
        product.setStock(rs.getInt("stock"));
        product.setDescription(rs.getString("description"));
        product.setCreatedDate(rs.getTimestamp("created_date"));
        product.setLastModifiedDate(rs.getTimestamp("last_modified_date"));

        return product;
    }
}
