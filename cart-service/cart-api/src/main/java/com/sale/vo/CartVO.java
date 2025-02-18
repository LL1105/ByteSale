package com.sale.vo;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class CartVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID。
     */
    private Long userId;

    /**
     * A list of CartItemVO objects representing the items in the cart.
     * Each item contains details such as product information, price, and quantity.
     */
    private List<CartItemVO> items;

    /**
     * 计算得出的购物车总价，此值会根据{@link CartVO#items}中每个CartItemVO的数量和价格实时计算更新。
     * 表示当前购物车所有商品的总价值。
     */
    private BigDecimal totalPrice; // 总价（实时计算）
}