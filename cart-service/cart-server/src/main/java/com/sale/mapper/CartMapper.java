package com.sale.mapper;

import com.sale.model.Cart;
import  com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sale.model.CartItem;
import org.apache.ibatis.annotations.*;

@Mapper
public interface CartMapper extends BaseMapper<Cart> {

    @Select("SELECT * FROM cart WHERE user_id = #{userId} LIMIT 1")
    Cart selectByUserId(@Param("userId") Long userId);

}
