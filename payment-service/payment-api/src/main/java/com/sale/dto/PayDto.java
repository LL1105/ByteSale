package com.sale.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 支付DTO
 */
@Data
public class PayDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 支付平台 1：小程序  2：H5  3：pc网页  4：app
     */
    @NotNull
    private Integer platform;

    /**
     * 订单号
     */
    @NotBlank
    private String orderNumber;

    /**
     * 订单标题
     */
    @NotBlank
    private String subject;

    /**
     * 价格
     */
    @NotNull
    private BigDecimal price;

    /**
     * 支付渠道 alipay：支付宝 wx：微信
     */
    @NotNull
    private String channel;

    /**
     * 支付种类
     */
    @NotNull
    private Integer payBillType;

    /**
     * 支付成功后通知接口地址
     */
    @NotBlank
    private String notifyUrl;

    /**
     * 支付成功后跳转页面
     */
    @NotBlank
    private String returnUrl;
}
