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
        Product product = productDao.getProductById(productId);

        if (product == null) {
            log.warn("Product ID: {} is not exist", productId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return product;
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        return productDao.createProduct(productRequest);
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {
        Product product = productDao.getProductById(productId);

        if (product == null) {
            log.warn("Product ID: {} is not exist", productId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        productDao.updateProduct(productId, productRequest);
    }

    @Override
    public void deleteProductById(Integer productId) {
        productDao.deleteProductById(productId);
    }

    @Override
    public List<Product> getProducts(GetProductsParams getProductsParams) {
        return productDao.getProducts(getProductsParams);
    }

    @Override
    public Integer countProducts(GetProductsParams getProductsParams) {
        return productDao.countProducts(getProductsParams);
    }
}
