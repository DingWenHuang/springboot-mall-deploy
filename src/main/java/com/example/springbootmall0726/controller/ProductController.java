package com.example.springbootmall0726.controller;

import com.example.springbootmall0726.constant.ProductCategory;
import com.example.springbootmall0726.dto.GetProductsParams;
import com.example.springbootmall0726.dto.ProductRequest;
import com.example.springbootmall0726.model.Product;
import com.example.springbootmall0726.service.ProductService;
import com.example.springbootmall0726.util.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "商品功能", description = "提供商品的CRUD API")
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @Operation(summary= "取得所有商品內容，預設回傳5筆數據")
    @GetMapping("/products")
    public ResponseEntity<Page<Product>> getProducts(@RequestParam(required = false) String search,
                                                     @RequestParam(required = false)ProductCategory category,
                                                     @RequestParam(defaultValue = "created_date") String orderBy,
                                                     @RequestParam(defaultValue = "DESC") String sort,
                                                     @RequestParam(defaultValue = "5") Integer limit,
                                                     @RequestParam(defaultValue = "0") Integer offset
    ) {
        // 將查詢商品參數封裝成物件
        GetProductsParams getProductsParams = new GetProductsParams();
        getProductsParams.setSearch(search);
        getProductsParams.setCategory(category);
        getProductsParams.setOrderBy(orderBy);
        getProductsParams.setSort(sort);
        getProductsParams.setLimit(limit);
        getProductsParams.setOffset(offset);

        // 取得商品列表
        List<Product> productList = productService.getProducts(getProductsParams);

        // 商品總筆數
        Integer total = productService.countProducts(getProductsParams);

        // 將商品列表與商品總筆數封裝成分頁物件
        Page<Product> productPage = new Page<>();
        productPage.setLimit(limit);
        productPage.setOffset(offset);
        productPage.setTotal(total);
        productPage.setResults(productList);

        return ResponseEntity.status(200).body(productPage);
    }

    @Operation(summary= "根據商品id取得該商品數據")
    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer productId) {

        // 根據商品id取得該筆商品資料
        Product product = productService.getProductById(productId);

        return ResponseEntity.status(200).body(product);
    }

    @Operation(summary= "新增商品到資料庫內，並回傳新增後的內容")
    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest) {

        // 建立商品，並取得建立後的商品id
        Integer productId = productService.createProduct(productRequest);

        // 根據商品id取得該筆商品資料
        Product product = productService.getProductById(productId);

        return ResponseEntity.status(201).body(product);
    }

    @Operation(summary= "根據商品id更新商品，並回傳更新後的內容")
    @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId,
                                                 @RequestBody @Valid ProductRequest productRequest) {

        // 根據商品id更新商品
        productService.updateProduct(productId, productRequest);

        // 根據商品id取得更新後的商品資料
        Product product  = productService.getProductById(productId);

        return ResponseEntity.status(200).body(product);
    }

    @Operation(summary= "根據商品id刪除商品，並回傳204 No Content")
    @DeleteMapping("/products/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer productId) {

        // 不管要刪除的商品是否存在，都回傳204 No Content
        productService.deleteProductById(productId);

        return ResponseEntity.status(204).build();
    }
}
