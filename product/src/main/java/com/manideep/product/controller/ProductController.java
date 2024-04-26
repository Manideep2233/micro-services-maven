package com.manideep.product.controller;

import com.manideep.product.dto.ProductInput;
import com.manideep.product.dto.ProductResponse;
import com.manideep.product.service.ProductService;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    public ProductController(ProductService productService){
        this.productService=productService;
    }

    @PostMapping("/add")
    public String addProduct(@RequestBody ProductInput input){
        return productService.addProduct(input);
    }

    @GetMapping("/getAll")
    public List<ProductResponse> getAll(){
        return productService.getAll();
    }
}
