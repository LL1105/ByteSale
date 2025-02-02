package com.sale.controller;

import com.sale.dto.ProductDTO;
import com.sale.service.ProductService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    @DubboReference
    private ProductService productService;

    @PostMapping("/add")
    public String addProduct(@RequestBody ProductDTO productDTO) {
        productService.addProduct(productDTO);
        return "Product added successfully!";
    }

    @GetMapping("/{id}")
    public ProductDTO getProduct(@PathVariable Long id) {
        return productService.getProduct(id);
    }
}