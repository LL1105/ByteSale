package com.sale.service.impl;

import com.sale.model.ProductDocument;
import com.sale.repository.ProductSearchRepository;
import com.sale.service.ProductSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductSearchServiceImpl implements ProductSearchService {

    @Autowired
    private ProductSearchRepository productSearchRepository;

    @Override
    public List<ProductDocument> searchByNameOrDescription(String keyword) {
        return productSearchRepository.findByNameOrDescription(keyword, keyword);
    }

    @Override
    public List<ProductDocument> searchByCategoryAndBrand(String category, String brand) {
        return productSearchRepository.findByCategoryAndBrand(category, brand);
    }

    @Override
    public List<ProductDocument> searchByPriceRange(Double minPrice, Double maxPrice) {
        return productSearchRepository.findByPriceBetween(minPrice, maxPrice);
    }
}