package com.sale.pay;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 支付结果
 */
@Data
@AllArgsConstructor
public class PayResult {
    
    private final boolean success;
    
    private final String body;
}
