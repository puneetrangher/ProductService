package com.scaler.productservice.service;

import com.scaler.productservice.models.Product;

import java.util.List;

public interface ProductService {

    Product getSingleProduct(Long id);
    Product addProduct(Product product);
    List<Product> getAllProducts();
}
