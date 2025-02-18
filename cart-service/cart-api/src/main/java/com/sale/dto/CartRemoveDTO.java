package com.sale.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 移除购物车商品DTO
 */
@Data
public class CartRemoveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @NotBlank(message = "用户ID不能为空")
    private Long userId;

    /**
     * 商品ID
     */
    @NotBlank(message = "商品ID不能为空")
    private Long skuId;
}