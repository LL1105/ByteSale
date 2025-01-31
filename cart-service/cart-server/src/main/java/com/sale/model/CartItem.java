package com.sale.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 购物车条目
 */
@TableName("CartItem")
@Data
public class CartItem implements Serializable {

    @TableField(exist = false)
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
    private Integer price;

    /**
     * 优惠券id
     */
    private Long discountId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }
    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }
    public Long getSpuId() {
        return spuId;
    }

    public void setSpuId(Long spuId) {
        this.spuId = spuId;
    }
    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
    public Long getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Long discountId) {
        this.discountId = discountId;
    }

    @Override
    public String toString() {
        return "cartItem{" +
                "id=" + id +
                ", cartId=" + cartId +
                ", skuId=" + skuId +
                ", spuId=" + spuId +
                ", storeId=" + storeId +
                ", quantity=" + quantity +
                ", price=" + price +
                ", discountId=" + discountId +
                "}";
    }
}
