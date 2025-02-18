package com.sale.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sale.model.CartItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CartItemMapper extends BaseMapper<CartItem> {
    // 根据用户ID和SKU查询购物车项
    @Select("SELECT * FROM cart_item WHERE cart_id = #{cartId} AND sku_id = #{skuId} LIMIT 1")
    CartItem selectByCartIdAndSkuId(@Param("cartId") Long cartId, @Param("skuId") Long skuId);
}