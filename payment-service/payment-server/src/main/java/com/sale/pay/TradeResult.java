package com.sale.pay;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TradeResult {
    
    private boolean success;
    
    private Integer payBillStatus;
    
    private String outTradeNo;
    
    private BigDecimal totalAmount;
}
