package com.scaler.productservice.service;

import com.scaler.productservice.models.Product;

public interface ProductService {

    Product getSingleProduct(Long id);
    Product addProduct(Product product);
}
