package com.sale.mapper;

import com.sale.model.Product;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ProductMapper {

    @Insert("INSERT INTO product (name, description, price, image_url, category_id, brand_id) " +
            "VALUES (#{name}, #{description}, #{price}, #{imageUrl}, #{categoryId}, #{brandId})")
    void insert(Product product);

    @Select("SELECT * FROM product WHERE id = #{id}")
    Product selectById(Long id);
}