package com.sale.mapper;

import com.sale.model.Inventory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface InventoryMapper {

    @Select("SELECT * FROM inventory WHERE product_id = #{productId}")
    Inventory selectByProductId(Long productId);

    @Update("UPDATE inventory SET stock = stock - #{quantity} WHERE product_id = #{productId}")
    void reduceStock(Long productId, Integer quantity);
}