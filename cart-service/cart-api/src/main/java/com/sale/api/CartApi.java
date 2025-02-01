package com.sale.api;

import com.sale.dto.CartAddDTO;
import com.sale.vo.CartVO;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService(version = "1.0.0")
public interface CartApi {
    Boolean addItem(CartAddDTO dto);
    CartVO getCart(Long userId);
}
