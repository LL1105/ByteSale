package com.sale.vo;


import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 支付交易状态查询VO
 */
@Data
public class TradeCheckVo implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 支付状态
     */
    private Integer payBillStatus;

    /**
     * 商户订单号
     */
    private String outTradeNo;

    /**
     * 支付金额
     */
    private BigDecimal totalAmount;
}
