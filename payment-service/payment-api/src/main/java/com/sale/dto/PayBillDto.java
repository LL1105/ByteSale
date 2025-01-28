package com.sale.dto;


import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 账单DTO
 */
@Data
public class PayBillDto implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /**
     * 订单号
     */
    @NotNull
    private String orderNumber;
}
