package com.example.springbootmall0726.dao;

import com.example.springbootmall0726.dto.GetProductsParams;
import com.example.springbootmall0726.dto.ProductRequest;
import com.example.springbootmall0726.model.Product;

import java.util.List;

public interface ProductDao {

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductById(Integer productId);

    List<Product> getProducts(GetProductsParams getProductsParams);

    Integer countProducts(GetProductsParams getProductsParams);

    void updateStock(Integer productId, Integer newStock);
}
