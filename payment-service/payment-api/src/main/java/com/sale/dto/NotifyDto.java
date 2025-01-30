package com.sale.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Map;

/**
 * 支付回调通知DTO
 */
@Data
public class NotifyDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 支付渠道 alipay：支付宝 wx：微信
     */
    @NotNull
    private String channel;

    /**
     * 回调参数
     */
    @NotNull
    private Map<String, String> params;
}
