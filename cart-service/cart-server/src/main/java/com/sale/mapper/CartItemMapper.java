package com.sale.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sale.model.CartItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CartItemMapper extends BaseMapper<CartItem> {

    @Select("SELECT * FROM cart_item WHERE user_id = #{userId} AND sku_id = #{skuId} LIMIT 1")
    CartItem selectByUserIdAndSku(@Param("userId") Long userId, @Param("skuId") Long skuId);
}
