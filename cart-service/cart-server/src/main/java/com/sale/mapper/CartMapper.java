package com.sale.mapper;

import com.sale.model.Cart;
import  com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sale.model.CartItem;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface CartMapper extends BaseMapper<Cart> {

    @Select("SELECT * FROM cart WHERE user_id = #{userId}")
    Cart selectByUserId(@Param("userId") Long userId);

    void insert(CartItem item);
}
