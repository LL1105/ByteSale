package com.sale;

import com.sale.dto.CartAddDTO;
import com.sale.mapper.CartItemMapper;
import com.sale.mapper.CartMapper;
import com.sale.model.CartItem;
import com.sale.api.ProductApi;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(properties = "dubbo.application.name=cart-service-test")
@ActiveProfiles("test")
public class CartServiceMockTest {

    @MockBean
    private CartMapper cartMapper;

    @MockBean
    private CartItemMapper cartItemMapper;

    @MockBean
    private RedisTemplate<String, Object> redisTemplate;

    @MockBean
    private ProductApi productApi; // Mock 远程服务

    @BeforeEach
    void setUp() {
        productApi = Mockito.mock(ProductApi.class);
    }

    @InjectMocks
    private com.sale.service.impl.CartServiceImpl cartService;

    @Test
    public void testAddItem_StockSufficient() {
        // 模拟商品库存充足
        when(productApi.checkStock(1001L, 2)).thenReturn(true);
        when(productApi.getPrice(1001L)).thenReturn(new BigDecimal("99.99"));

        CartAddDTO dto = new CartAddDTO();
        dto.setUserId(1L);
        dto.setSkuId(1001L);
        dto.setQuantity(2);

        assertDoesNotThrow(() -> cartService.addItem(dto));

        // 验证数据库是否被调用
        verify(cartItemMapper, times(1)).insert(any(CartItem.class));
    }

    @Test
    public void testAddItem_StockInsufficient() {
        // 模拟商品库存不足
        when(productApi.checkStock(1001L, 5)).thenReturn(false);

        CartAddDTO dto = new CartAddDTO();
        dto.setUserId(1L);
        dto.setSkuId(1001L);
        dto.setQuantity(5);

        Exception exception = assertThrows(RuntimeException.class, () -> cartService.addItem(dto));
        assertEquals("商品库存不足", exception.getMessage());

        // 验证数据库未被调用
        verify(cartItemMapper, never()).insert(any(CartItem.class));
    }
}
