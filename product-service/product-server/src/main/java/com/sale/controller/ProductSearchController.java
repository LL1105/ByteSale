package com.sale.controller;

import com.sale.model.ProductDocument;
import com.sale.service.ProductSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search")
public class ProductSearchController {

    @Autowired
    private ProductSearchService productSearchService;

    @GetMapping("/name-or-description")
    public List<ProductDocument> searchByNameOrDescription(@RequestParam String keyword) {
        return productSearchService.searchByNameOrDescription(keyword);
    }

    @GetMapping("/category-and-brand")
    public List<ProductDocument> searchByCategoryAndBrand(
            @RequestParam String category,
            @RequestParam String brand) {
        return productSearchService.searchByCategoryAndBrand(category, brand);
    }

    @GetMapping("/price-range")
    public List<ProductDocument> searchByPriceRange(
            @RequestParam Double minPrice,
            @RequestParam Double maxPrice) {
        return productSearchService.searchByPriceRange(minPrice, maxPrice);
    }
}