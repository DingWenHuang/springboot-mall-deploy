package com.example.springbootmall0726.service.impl;

import com.example.springbootmall0726.constant.ProductCategory;
import com.example.springbootmall0726.dao.ProductDao;
import com.example.springbootmall0726.dto.GetProductsParams;
import com.example.springbootmall0726.dto.ProductRequest;
import com.example.springbootmall0726.model.Product;
import com.example.springbootmall0726.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);

    @Override
    public Product getProductById(Integer productId) {

        // 根據 productId 從資料庫中取得商品資料
        Product product = productDao.getProductById(productId);

        // 如果商品資料為 null，則回傳 404 NOT_FOUND，並且在 console 中印出警告訊息
        if (product == null) {
            log.warn("Product ID: {} is not exist", productId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return product;
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {

        // 根據 productRequest 建立商品資料
        return productDao.createProduct(productRequest);
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {

        // 根據 productId 從資料庫中取得商品資料
        Product product = productDao.getProductById(productId);

        // 如果商品資料為 null，則回傳 404 NOT_FOUND，並且在 console 中印出警告訊息
        if (product == null) {
            log.warn("Product ID: {} is not exist", productId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        // 如果商品資料不為 null，則更新該筆資料
        productDao.updateProduct(productId, productRequest);
    }

    @Override
    public void deleteProductById(Integer productId) {

        // 根據 productId 從資料庫中刪除該筆資料
        productDao.deleteProductById(productId);
    }

    @Override
    public List<Product> getProducts(GetProductsParams getProductsParams) {

        // 根據 productQueryParams 從資料庫中篩選出符合條件的商品資料
        return productDao.getProducts(getProductsParams);
    }

    @Override
    public Integer countProducts(GetProductsParams getProductsParams) {

        // 根據 productQueryParams 從資料庫中篩選出符合條件的商品總數
        return productDao.countProducts(getProductsParams);
    }
}
