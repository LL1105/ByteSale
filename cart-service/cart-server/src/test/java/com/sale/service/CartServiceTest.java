package com.sale.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sale.api.ProductApi;
import com.sale.dto.CartAddDTO;
import com.sale.mapper.CartItemMapper;
import com.sale.mapper.CartMapper;
import com.sale.model.Cart;
import com.sale.model.CartItem;
import com.sale.service.impl.CartServiceImpl;
import com.sale.vo.CartVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CartServiceTest {

    private static final String CART_REDIS_KEY_PREFIX = "cart:";

    @Mock
    private CartMapper cartMapper;

    @Mock
    private CartItemMapper cartItemMapper;

    @Mock
    private ProductApi productApi;

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private ValueOperations<String, Object> valueOperations;

    @InjectMocks
    private CartServiceImpl cartService;

    private CartAddDTO testCartAddDTO;

    @BeforeEach
    void setUp() {
        // 初始化测试数据
        testCartAddDTO = new CartAddDTO();
        testCartAddDTO.setUserId(1L);
        testCartAddDTO.setSkuId(100L);
        testCartAddDTO.setQuantity(2);

        // 模拟外部依赖行为
        lenient().when(productApi.checkStock(anyLong(), anyInt())).thenReturn(true);
        lenient().when(productApi.getPrice(anyLong())).thenReturn(BigDecimal.valueOf(99.99));
        lenient().when(cartMapper.selectByUserId(anyLong())).thenReturn(null);
        lenient().when(redisTemplate.opsForValue()).thenReturn(valueOperations);

        // 模拟购物车插入操作
        lenient().doAnswer(invocation -> {
            Cart cart = invocation.getArgument(0);
            cart.setId(1L);
            return 1;
        }).when(cartMapper).insert(any(Cart.class));
    }

    @Test
    void testAddItem_NewCartItem_Success() {
        // Mock the cartMapper to return null initially and then return the created cart
        when(cartMapper.selectByUserId(anyLong()))
                .thenReturn(null)
                .thenAnswer(invocation -> {
                    Cart cart = new Cart();
                    cart.setId(1L);
                    cart.setUserId(1L);
                    cart.setCreatedAt(LocalDateTime.now());
                    cart.setUpdatedAt(LocalDateTime.now());
                    return cart;
                });

        // Mock the cartItemMapper to simulate the insertion of a CartItem
        doAnswer(invocation -> {
            CartItem cartItem = invocation.getArgument(0);
            cartItem.setId(1L); // Simulate setting the ID after insertion
            return 1; // Return 1 to indicate successful insertion
        }).when(cartItemMapper).insert(any(CartItem.class));

        // Mock the cartItemMapper to return the inserted CartItem
        when(cartItemMapper.selectList(any(QueryWrapper.class)))
                .thenAnswer(invocation -> {
                    List<CartItem> items = new ArrayList<>();
                    CartItem cartItem = new CartItem();
                    cartItem.setId(1L);
                    cartItem.setCartId(1L);
                    cartItem.setSkuId(100L);
                    cartItem.setQuantity(2);
                    cartItem.setPrice(BigDecimal.valueOf(99.99));
                    cartItem.setTotalPrice(BigDecimal.valueOf(199.98));
                    items.add(cartItem);
                    return items;
                });

        // Execute the addItem method
        cartService.addItem(testCartAddDTO);

        // Verify that the cart was created
        Cart cart = cartMapper.selectByUserId(testCartAddDTO.getUserId());
        assertNotNull(cart, "购物车应该被创建");

        // Verify that the cart items were added
        List<CartItem> items = cartItemMapper.selectList(
                new QueryWrapper<CartItem>().eq("cart_id", cart.getId())
        );
        assertFalse(items.isEmpty(), "购物车项应该被添加");

        // Print the results
        System.out.println("Created Cart: " + cart);
        System.out.println("Cart Items: " + items);
    }

    @Test
    void testAddItem_ExistingCart_Success() {
        // 准备模拟已存在的购物车
        Cart existingCart = new Cart();
        existingCart.setId(1L);
        existingCart.setUserId(1L);
        existingCart.setCreatedAt(LocalDateTime.now());
        existingCart.setUpdatedAt(LocalDateTime.now());

        // 模拟外部依赖
        when(cartMapper.selectByUserId(anyLong())).thenReturn(existingCart);
        when(cartItemMapper.selectByCartIdAndSkuId(anyLong(), anyLong())).thenReturn(null);
        when(productApi.checkStock(anyLong(), anyInt())).thenReturn(true);
        when(productApi.getPrice(anyLong())).thenReturn(BigDecimal.valueOf(99.99));
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);

        // 执行测试方法
        assertDoesNotThrow(() -> cartService.addItem(testCartAddDTO));

        // 验证方法调用
        verify(cartMapper, never()).insert(any(Cart.class));
        verify(cartItemMapper).insert(any(CartItem.class));
        verify(valueOperations, times(1)).set(
                eq(CART_REDIS_KEY_PREFIX + testCartAddDTO.getUserId()),
                any(CartVO.class),
                eq(30L),
                eq(TimeUnit.MINUTES)
        );
    }
}