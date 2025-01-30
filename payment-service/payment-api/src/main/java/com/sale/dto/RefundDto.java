package com.sale.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 退款DTO
 */
@Data
public class RefundDto {

    /**
     * 订单号
     */
    @NotBlank
    private String orderNumber;

    /**
     * 退款金额
     */
    @NotNull
    private BigDecimal amount;

    /**
     * 退款渠道 alipay：支付宝 wx：微信
     */
    @NotNull
    private String channel;

    /**
     * 退款原因
     */
    private String reason;
}
