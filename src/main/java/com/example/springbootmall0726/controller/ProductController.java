package com.example.springbootmall0726.controller;

import com.example.springbootmall0726.constant.ProductCategory;
import com.example.springbootmall0726.dto.GetProductsParams;
import com.example.springbootmall0726.dto.ProductRequest;
import com.example.springbootmall0726.model.Product;
import com.example.springbootmall0726.service.ProductService;
import com.example.springbootmall0726.util.Page;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<Page<Product>> getProducts(@RequestParam(required = false) String search,
                                                     @RequestParam(required = false)ProductCategory category,
                                                     @RequestParam(defaultValue = "created_date") String orderBy,
                                                     @RequestParam(defaultValue = "DESC") String sort,
                                                     @RequestParam(defaultValue = "5") Integer limit,
                                                     @RequestParam(defaultValue = "0") Integer offset
    ) {
        GetProductsParams getProductsParams = new GetProductsParams();
        getProductsParams.setSearch(search);
        getProductsParams.setCategory(category);
        getProductsParams.setOrderBy(orderBy);
        getProductsParams.setSort(sort);
        getProductsParams.setLimit(limit);
        getProductsParams.setOffset(offset);

        List<Product> productList = productService.getProducts(getProductsParams);

        Integer total = productService.countProducts(getProductsParams);

        Page<Product> productPage = new Page<>();
        productPage.setLimit(limit);
        productPage.setOffset(offset);
        productPage.setTotal(total);
        productPage.setResults(productList);

        return ResponseEntity.status(200).body(productPage);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer productId) {
        Product product = productService.getProductById(productId);

        return ResponseEntity.status(200).body(product);
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest) {
        Integer productId = productService.createProduct(productRequest);

        Product product = productService.getProductById(productId);

        return ResponseEntity.status(201).body(product);
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId,
                                                 @RequestBody @Valid ProductRequest productRequest) {
        productService.updateProduct(productId, productRequest);

        Product product  = productService.getProductById(productId);

        return ResponseEntity.status(200).body(product);
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity deleteProduct(@PathVariable Integer productId) {
        productService.deleteProductById(productId);

        return ResponseEntity.status(204).build();
    }
}
