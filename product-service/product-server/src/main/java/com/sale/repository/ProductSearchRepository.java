package com.sale.repository;

import com.sale.model.ProductDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductSearchRepository extends ElasticsearchRepository<ProductDocument, Long> {

    /**
     * 根据商品名称或描述进行全文搜索
     *
     * @param name        商品名称
     * @param description 商品描述
     * @return 匹配的商品列表
     */
    List<ProductDocument> findByNameOrDescription(String name, String description);

    /**
     * 根据分类和品牌进行过滤搜索
     *
     * @param category 商品分类
     * @param brand    商品品牌
     * @return 匹配的商品列表
     */
    List<ProductDocument> findByCategoryAndBrand(String category, String brand);

    /**
     * 根据价格范围进行搜索
     *
     * @param minPrice 最低价格
     * @param maxPrice 最高价格
     * @return 匹配的商品列表
     */
    List<ProductDocument> findByPriceBetween(Double minPrice, Double maxPrice);
}