package com.scaler.productservice.service;

import com.scaler.productservice.exceptions.ProductDoesNotExistException;
import com.scaler.productservice.models.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("selfProductService") // This tells Spring to make this class a service
public class SelfProductService implements ProductService {
    @Override
    public Product getSingleProduct(Long id) throws ProductDoesNotExistException {
        return null;
    }

    @Override
    public Product addProduct(Product product) {
        return null;
    }

    @Override
    public List<Product> getAllProducts() {
        return null;
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        return null;
    }

    @Override
    public Product replaceProduct(Long id, Product product) {
        return null;
    }

    @Override
    public Product deleteProduct(Long id) {
        return null;
    }
}
