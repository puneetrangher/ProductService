package com.scaler.productservice.service;

import com.scaler.productservice.dtos.FakeStoreProductDto;
import com.scaler.productservice.models.Category;
import com.scaler.productservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    // get all products
    @Override
    public List<Product> getAllProducts() {
        /*
        1. Create a list of product dto to store the result from the fakestore api
        2. Create another list of Products
        3. Iterate over product dto list to convert and store them in product list
        4. return product list
         */

        /*
        You cannot directly store the response of getForObject as a list of dto object
        because of Type Erasure functionality of Java Generics at runtime. From Java 1.6 onwards
        when Generics were introduced, in order to maintain backward compatibility, Java developers
        remove Type checking at runtime.
        Because of this when you try to store the response of getForObject() method as
        List<FakeStoreProductDto>.class, it internally converts that to List.class and then
        stores the product details as HashMap in the response.
        As lists cannot work here, we have to use Arrays.
         */

        /*FakeStoreProductDto[] dtoList = restTemplate.getForObject(
                "https://fakestoreapi.com/products",
                FakeStoreProductDto[].class
        );

        // Create a list of Product
        List<Product> productList= new ArrayList<>();

        // iterate over dto array and add all products to product list by converting them
        for (FakeStoreProductDto dto: dtoList) {
            productList.add(convertProductDtoToProduct(dto));
        }

        return productList;*/

        //Bard's implementation:
        ResponseEntity<FakeStoreProductDto[]> response = restTemplate.getForEntity("https://fakestoreapi.com/products", FakeStoreProductDto[].class);
        FakeStoreProductDto[] dtos = response.getBody();
        return Arrays.stream(dtos)
                .map(this::convertProductDtoToProduct)
                .collect(Collectors.toList());
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


    // Add a product
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

    @Override
    public Product updateProduct(Long id, Product product) {
        return new Product();
    }

    @Override
    public Product replaceProduct(Long id, Product product) {
        /*
        Replace product is a method that will map to PUT API on fakestore side
        We cannot use restTemplate.put() method here as the return type of put()
        method is void and the PUT API on fakestore returns a product dto.
        So we have to use a lower level API from restTemplate which is either
        restTemplate.exchange() or restTemplate.execute() or we can also use the
        code of any postForObject method from restTemplate as Naman has done on
        his code.
         */

        FakeStoreProductDto dto = convertProductToProductDto(product);

        // my implementation as per what Naman has implemented
//        RequestCallback requestCallback = restTemplate.httpEntityCallback(dto, FakeStoreProductDto.class);
//        HttpMessageConverterExtractor<FakeStoreProductDto> responseExtractor = new HttpMessageConverterExtractor(FakeStoreProductDto.class, restTemplate.getMessageConverters());
//        return restTemplate.execute("https://fakestoreapi.com/products/" + id, HttpMethod.POST, requestCallback, responseExtractor);

        // Bard's implementation
        ResponseEntity<FakeStoreProductDto> response = restTemplate.exchange(
                "https://fakestoreapi.com/products/" + id,
                HttpMethod.PUT,
                new HttpEntity<>(dto),
                FakeStoreProductDto.class
        );
        FakeStoreProductDto responseDto = response.getBody();
        return convertProductDtoToProduct(responseDto);

        /*
        To test this via postman send the below JSON in the body
        assuming that we are changing product with id = 1
        localhost:8080/products/replace/1
        {
            "id": 1,
            "title": "Updated Product Title",
            "price": 49.99,
            "category": {
                "id": 1,  // Assuming a numerical category ID
                "name": "Electronics"
            },
            "description": "This is a new description for the replaced product.",
            "image": "https://example.com/product-image.jpg"
        }
         */
    }

}
