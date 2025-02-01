package com.sale.dto;

import lombok.Data;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * 添加商品到购物车DTO
 */
@Data
public class CartAddDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @NotBlank(message = "用户ID不能为空")
    private Long userId;

    /**
     * 商品ID
     */
    @NotBlank(message = "SKU ID不能为空")
    private Long skuId;

    /**
     *商品数量
     */
    @NotNull(message = "商品数量不能为空")
    @Min(value = 1, message = "商品数量至少为1")
    private Integer quantity;
}