package com.sale.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.*;

/**
 * 购物车条目
 */
@TableName("cart_item")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItem implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 购物车id
     */
    private Long cartId;

    /**
     * SKU=Stock Keeping Unit（库存量单位）
     */
    private Long skuId;

    /**
     * SPU = Standard Product Unit （标准产品单位）
     */
    private Long spuId;

    /**
     * 商家ID
     */
    private Long storeId;

    /**
     * 商品数量
     */
    private Integer quantity;

    /**
     * 商品价格+单价
     */
    private BigDecimal price;

    /**
     * 优惠券id
     */
    private Long discountId;


    @TableField("created_at")
    private LocalDateTime createdAt;

    @TableField("updated_at")
    private LocalDateTime updatedAt;
}
