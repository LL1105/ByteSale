package com.sale.api;

import org.apache.dubbo.config.annotation.DubboService;
import java.math.BigDecimal;

@DubboService(version = "1.0.0")
public interface ProductApi {
    Boolean checkStock(Long skuId, Integer quantity);
    BigDecimal getPrice(Long skuId);
}
