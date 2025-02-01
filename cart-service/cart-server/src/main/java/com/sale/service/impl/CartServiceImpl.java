package com.sale.service.impl;

import com.sale.dto.CartAddDTO;
import com.sale.mapper.CartItemMapper;
import com.sale.mapper.CartMapper;
import com.sale.model.Cart;
import com.sale.model.CartItem;
import com.sale.service.CartService;
import com.sale.vo.CartVO;
import com.sale.api.ProductApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private CartItemMapper cartItemMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @DubboReference(version = "1.0.0")
    private ProductApi productApi; // 远程调用商品服务

    private static final String CART_REDIS_KEY_PREFIX = "cart:";

    @Override
    @Transactional
    public void addItem(CartAddDTO dto) {
        Long userId = dto.getUserId();
        Long skuId = dto.getSkuId();
        Integer quantity = dto.getQuantity();

        // 1. 校验库存
        Boolean stockCheck = productApi.checkStock(skuId, quantity);
        if (!stockCheck) {
            log.warn("商品 [{}] 库存不足，无法加入购物车", skuId);
            throw new RuntimeException("商品库存不足");
        }

        // 2. 查询商品当前价格
        BigDecimal price = productApi.getPrice(skuId);
        if (Objects.isNull(price)) {
            throw new RuntimeException("无法获取商品价格");
        }

        // 3. 查询购物车（缓存优先）
        String redisKey = CART_REDIS_KEY_PREFIX + userId;
        CartVO cartVO = (CartVO) redisTemplate.opsForValue().get(redisKey);

        Cart cart;
        if (cartVO == null) {
            cart = cartMapper.selectByUserId(userId);
            if (cart == null) {
                cart = Cart.builder()
                        .userId(userId)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build();
                cartMapper.insert(cart);
            }
        } else {
            cart = cartMapper.selectByUserId(userId);
            if (cart == null) {
                cart = Cart.builder()
                        .userId(userId)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build();
                cartMapper.insert(cart);
            }
        }

        if (cart == null) {
            log.error("购物车信息确实，请检查数据库操作");
            throw new RuntimeException("购物车信息缺失");
        }

        // 4. 处理购物车条目
        CartItem cartItem = cartItemMapper.selectByUserIdAndSku(userId, skuId);
        if (cartItem == null) {
            cartItem = CartItem.builder()
                    .cartId(cart.getId())
                    .skuId(skuId)
                    .quantity(quantity)
                    .price(price)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            cartItemMapper.insert(cartItem);
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItem.setUpdatedAt(LocalDateTime.now());
            cartItemMapper.updateById(cartItem);
        }

        // 5. 更新 Redis 缓存
        if (cartVO != null) {
            redisTemplate.opsForValue().set(redisKey, cartVO, 30, TimeUnit.MINUTES);
        }
        log.info("用户 [{}] 成功添加商品 [{}] 到购物车，数量 [{}]", userId, skuId, quantity);
    }
}
