package com.sale.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 支付回调结果VO
 */
@Data
public class NotifyVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商户订单号
     */
    private String outTradeNo;

    /**
     * 回调返回结果
     */
    private String payResult;
}
