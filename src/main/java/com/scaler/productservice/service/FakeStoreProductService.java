package com.scaler.productservice.service;

import com.scaler.productservice.dtos.FakeStoreProductDto;
import com.scaler.productservice.models.Category;
import com.scaler.productservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FakeStoreProductService implements ProductService{

    // In this service we have to call the fakestore API and
    // Spring provides a RestTemplate Class under WebClient add-on
    // that will be used to call the third party APIs
    // Moreover, we need to create just one object of the RestTemplate
    // class and we will store this in Spring's Application Context as
    // a Bean
    // In order to invoke the FakeStore API from this service class
    // we need to create a reference for RestTemplate and then create
    // a constructor that will take the RestTemplate object as the parameter
    // and use @Autowire annotation with this constructor which will tell
    // Spring to get the RestTemplate object (Bean) from Application Context

    // Moreover, when you use @Autowired annotation, you have to use
    // @Service at the class level

    private RestTemplate restTemplate;


    @Autowired
    public FakeStoreProductService(RestTemplate restTemplateObj) {
        this.restTemplate = restTemplateObj;
    }

    // method to convert productDto to normal product
    public Product convertProductDtoToProduct(FakeStoreProductDto fakeStoreProductDto) {
        Product product = new Product();
        product.setId(fakeStoreProductDto.getId());
        product.setTitle(fakeStoreProductDto.getTitle());
        product.setPrice(fakeStoreProductDto.getPrice());
        product.setImageUrl(fakeStoreProductDto.getImage());
        product.setDescription(fakeStoreProductDto.getDescription());

        // to set the category, you first have to create a new Object of Category class
        // and on that Object you have to trigger setName() method of Category class
        product.setCategory(new Category());
        product.getCategory().setName(fakeStoreProductDto.getCategory());

        return product;
    }

    @Override
    public Product getSingleProduct(Long id) {
        FakeStoreProductDto productDto = restTemplate.getForObject(
                 "https://fakestoreapi.com/products/" + id,
                 FakeStoreProductDto.class
        );

        // the below line is added by IntelliJ/Spring to make sure
        // return is not getting called on a NULL object as
        // productDto can be NULL
        assert productDto != null;
        return convertProductDtoToProduct(productDto);
    }

    // method to convert a given Product to product dto
    public FakeStoreProductDto convertProductToProductDto(Product product) {
        FakeStoreProductDto dto = new FakeStoreProductDto();
        // ... Map properties from Product to FakeStoreProductDto ...
        dto.setId(product.getId());
        dto.setTitle(product.getTitle());
        dto.setPrice(product.getPrice());
        dto.setCategory(product.getCategory().getName());
        dto.setDescription(product.getDescription());
        dto.setImage(product.getImageUrl());
        return dto;
    }

    @Override
    public Product addProduct(Product product) {
        FakeStoreProductDto dto = convertProductToProductDto(product);
        FakeStoreProductDto responseProduct = restTemplate.postForObject(
                "https://fakestoreapi.com/products",
                dto,
                FakeStoreProductDto.class);
        assert responseProduct != null;
        return convertProductDtoToProduct(responseProduct);
    }

}
