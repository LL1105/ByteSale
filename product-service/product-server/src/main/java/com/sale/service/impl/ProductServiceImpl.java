package com.sale.service.impl;

import com.sale.dto.ProductDTO;
import com.sale.mapper.ProductMapper;
import com.sale.model.Product;
import com.sale.service.ProductService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

@DubboService
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public void addProduct(ProductDTO productDTO) {
        Product product = new Product();
        BeanUtils.copyProperties(productDTO, product);
        productMapper.insert(product);
    }

    @Override
    public ProductDTO getProduct(Long id) {
        Product product = productMapper.selectById(id);
        ProductDTO productDTO = new ProductDTO();
        BeanUtils.copyProperties(product, productDTO);
        return productDTO;
    }
}