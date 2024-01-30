package com.scaler.productservice.service;

import com.scaler.productservice.exceptions.ProductDoesNotExistException;
import com.scaler.productservice.models.Product;

import java.util.List;

public interface ProductService {

    Product getSingleProduct(Long id) throws ProductDoesNotExistException;
    Product addProduct(Product product);
    List<Product> getAllProducts();
    Product updateProduct(Long id, Product product);
    Product replaceProduct(Long id, Product product);
    Product deleteProduct(Long id);
}
