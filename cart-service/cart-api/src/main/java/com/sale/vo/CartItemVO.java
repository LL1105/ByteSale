package com.sale.vo;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 购物车详情VO
 */
@Data
public class CartItemVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品SKU ID。
     */
    private Long skuId;

    /**
     * 商品SPU ID。
     */
    private Long spuId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品价格。
     */
    private BigDecimal price;

    /**
     * 商品数量。
     */
    private Integer quantity;

    /**
     * 商品小计
     */
    private BigDecimal subtotal;
}