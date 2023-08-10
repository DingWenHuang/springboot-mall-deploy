package com.example.springbootmall0726.service;

import com.example.springbootmall0726.constant.ProductCategory;
import com.example.springbootmall0726.dto.GetProductsParams;
import com.example.springbootmall0726.dto.ProductRequest;
import com.example.springbootmall0726.model.Product;

import java.util.List;

public interface ProductService {

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductById(Integer productId);

    List<Product> getProducts(GetProductsParams getProductsParams);

    Integer countProducts(GetProductsParams getProductsParams);
}
