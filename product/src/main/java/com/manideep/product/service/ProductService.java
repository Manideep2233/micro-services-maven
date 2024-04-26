package com.manideep.product.service;

import com.manideep.product.dto.ProductInput;
import com.manideep.product.dto.ProductResponse;
import com.manideep.product.model.Product;
import com.manideep.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository=productRepository;
    }

    public String addProduct(ProductInput input){
        Product product = Product.builder()
                .name(input.getName())
                .description(input.getDesc())
                .price(input.getPrice())
                .build();
        productRepository.save(product);
        return "Successfully product saved";
    }


    public List<ProductResponse> getAll(){
        List<Product> allProducts = productRepository.findAll();
        return allProducts.stream().map(x->ProductResponse.builder()
                .name(x.getName())
                .desc(x.getDescription())
                .productId(x.getId())
                .price(x.getPrice())
                .build()
        ).toList();
    }
}
