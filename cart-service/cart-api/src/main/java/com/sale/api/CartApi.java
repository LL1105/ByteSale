package com.sale.api;

import com.sale.dto.CartAddDTO;
import com.sale.vo.CartVO;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService(version = "1.0.0")
public interface CartApi {
    Boolean addItem(CartAddDTO dto);
    CartVO getCartList(Long userId);
    Boolean deleteCartItem(Long userId, Long skuId);
    Boolean updateCartItemStatus(Long userId, Long skuId, Integer status);
    Boolean selectAllCartItems(Long userId);
    Boolean clearCart(Long userId);
}
