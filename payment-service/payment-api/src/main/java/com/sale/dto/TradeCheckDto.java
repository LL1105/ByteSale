package com.sale.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 交易状态DTO
 */
@Data
public class TradeCheckDto implements Serializable {

    /**
     * 商户订单号
     */
    @NotBlank
    private String outTradeNo;

    /**
     * 支付渠道 alipay：支付宝 wx：微信
     */
    @NotBlank
    private String channel;
}
