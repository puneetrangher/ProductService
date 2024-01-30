package com.scaler.productservice.controllers;

import com.scaler.productservice.exceptions.ProductDoesNotExistException;
import com.scaler.productservice.models.Product;
import com.scaler.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    // The controller will have to call the Product service and for that
    // we need to create a reference of the service

    private ProductService productService;

    // We will not create an object of the product service directly but,
    // instead we will create a constructor and pass the product service
    // Object in it and use @Autowired annotation for this
    @Autowired
    public ProductController(@Qualifier("selfProductService") ProductService productServiceObj) {
        this.productService = productServiceObj;
    }

    @GetMapping()
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getSingleProduct(@PathVariable("id") Long id) throws ProductDoesNotExistException {
        System.out.println("This is controller service");
        return productService.getSingleProduct(id);
    }

    @PostMapping()
    public Product addProduct(@RequestBody() Product product) {
        // As this is adding a product, according to me, we don't have to return anything
        // but Naman is return a product here

        return productService.addProduct(product);
    }

    @PatchMapping("/update/{id}")
    public Product updateProduct(@PathVariable("id") Long id, @RequestBody Product product) {
        // As this is updating a product, according to me, we don't have to return anything
        // but Naman is return a product here
        return productService.updateProduct(id, product);
    }

    @PutMapping("/replace/{id}")
    public Product replaceProduct(@PathVariable("id") Long id, @RequestBody Product product) {
        // As this is updating a product, according to me, we don't have to return anything
        // but Naman is return a product here
        return productService.replaceProduct(id, product);
    }

    @DeleteMapping("/delete/{id}")
    public Product deleteProduct(@PathVariable("id") Long id){
        // No need to return anything
        return productService.deleteProduct(id);
//        System.out.println(response);

    }

}
