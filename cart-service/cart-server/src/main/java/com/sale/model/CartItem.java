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
     * 主键ID。
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 购物车ID。
     */
    @TableField("cart_id")
    private Long cartId;

    /**
     * 商品SKU ID。
     */
    @TableField("sku_id")
    private Long skuId;

    /**
     * 商品SPU ID。
     */
    @TableField("spu_id")
    private Long spuId;

    /**
     * 商店ID。
     */
    @TableField("store_id")
    private Long storeId;

    /**
     * 商品数量。
     */
    @TableField("quantity")
    private Integer quantity;

    /**
     * 商品价格。
     */
    @TableField("price")
    private BigDecimal price;

    /**
     * 商品总价。
     */
    @TableField("total_price")
    private BigDecimal totalPrice;

    /**
     * 折扣ID，关联折扣详情的标识。
     */
    @TableField("discount_id")
    private Long discountId;

    /**
     * 记录创建时间。
     */
    @TableField("created_at")
    private LocalDateTime createdAt;

    /**
     * 记录最后更新的时间。
     */
    @TableField("updated_at")
    private LocalDateTime updatedAt;

    /**
     * 商品条目状态。
     */
    @TableField("status")
    private Integer status;
}
