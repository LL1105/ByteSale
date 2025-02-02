package com.sale.mapper;

import com.sale.model.Promotion;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PromotionMapper {

    @Insert("INSERT INTO promotion (product_id, promotion_type, discount, start_time, end_time) " +
            "VALUES (#{productId}, #{promotionType}, #{discount}, #{startTime}, #{endTime})")
    void insert(Promotion promotion);
}