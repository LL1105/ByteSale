package com.sale.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sale.api.ProductApi;
import com.sale.dto.CartAddDTO;
import com.sale.mapper.CartItemMapper;
import com.sale.mapper.CartMapper;
import com.sale.model.Cart;
import com.sale.model.CartItem;
import com.sale.service.CartService;
import com.sale.vo.CartItemVO;
import com.sale.vo.CartVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 购物车服务实现类
 * 提供购物车的增删改查操作，并支持缓存机制
 */
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
    private ProductApi productApi;

    private static final String CART_REDIS_KEY_PREFIX = "cart:";

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addItem(CartAddDTO dto) {
        Long userId = dto.getUserId();
        Long skuId = dto.getSkuId();
        Integer quantity = dto.getQuantity();

        log.info("开始处理添加购物车请求 - 用户ID: {}, 商品SKU: {}, 数量: {}", userId, skuId, quantity);

        // 库存检查
        Boolean stockCheck = productApi.checkStock(skuId, quantity);
        if (!stockCheck) {
            log.warn("商品 [{}] 库存不足，无法加入购物车", skuId);
            throw new RuntimeException("商品库存不足");
        }

        // 获取商品价格
        BigDecimal price = productApi.getPrice(skuId);
        if (Objects.isNull(price)) {
            log.error("无法获取商品 [{}] 的价格", skuId);
            throw new RuntimeException("无法获取商品价格");
        }

        // 查找或创建购物车
        Cart cart = findOrCreateCart(userId);
        log.info("购物车处理结果 - 购物车ID: {}", cart.getId());

        // 处理购物车条目
        handleCartItem(cart, userId, skuId, quantity, price);

        // 验证数据库操作
        Cart verifyCart = cartMapper.selectByUserId(userId);
        log.info("数据库验证 - 查询购物车结果: {}", verifyCart);
    }

    private Cart findOrCreateCart(Long userId) {
        Cart cart = cartMapper.selectByUserId(userId);
        if (cart == null) {
            log.info("创建新购物车 - 用户ID: {}", userId);
            cart = Cart.builder()
                    .userId(userId)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            // 添加调试日志
            int insertResult = cartMapper.insert(cart);
            log.info("购物车插入结果: {}", insertResult);
        }
        return cart;
    }

    /**
     * 获取商品价格
     *
     * @param skuId 商品SKU ID
     * @return 商品价格
     * @throws RuntimeException 无法获取价格时抛出异常
     */
    private BigDecimal getProductPrice(Long skuId) {
        BigDecimal price = productApi.getPrice(skuId);
        if (Objects.isNull(price)) {
            throw new RuntimeException("无法获取商品价格");
        }
        return price;
    }

    /**
     * 查找或创建购物车
     *
     * @param userId 用户ID
     * @return 购物车实体
     */

    /**
     * 处理购物车条目
     *
     * @param cart      购物车
     * @param userId    用户ID
     * @param skuId     商品SKU ID
     * @param quantity  商品数量
     * @param price     商品价格
     */
    private void handleCartItem(Cart cart, Long userId, Long skuId, Integer quantity, BigDecimal price) {
        CartItem cartItem = cartItemMapper.selectByUserIdAndSku(userId, skuId);

        if (cartItem == null) {
            // 新增购物车项
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
            // 更新购物车项数量
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItem.setUpdatedAt(LocalDateTime.now());
            cartItemMapper.updateById(cartItem);
        }
    }

    /**
     * 更新 Redis 缓存
     *
     * @param userId 用户ID
     */
    private void updateRedisCache(Long userId) {
        String redisKey = CART_REDIS_KEY_PREFIX + userId;
        CartVO updatedCartVO = fetchCartVO(userId);

        if (updatedCartVO != null) {
            redisTemplate.opsForValue().set(
                    redisKey,
                    updatedCartVO,
                    30,
                    TimeUnit.MINUTES
            );
        }
    }

    /**
     * 获取完整的购物车信息
     *
     * @param userId 用户ID
     * @return 购物车 VO 对象
     */
    private CartVO fetchCartVO(Long userId) {
        Cart cart = cartMapper.selectByUserId(userId);
        if (cart == null) {
            return null;
        }

        // 查询购物车项
        LambdaQueryWrapper<CartItem> queryWrapper = Wrappers.lambdaQuery(CartItem.class)
                .eq(CartItem::getCartId, cart.getId());
        List<CartItem> cartItems = cartItemMapper.selectList(queryWrapper);

        // 构建购物车 VO
        CartVO cartVO = new CartVO();
        cartVO.setUserId(userId);

        List<CartItemVO> cartItemVOList = cartItems.stream().map(item -> {
            CartItemVO cartItemVO = new CartItemVO();
            cartItemVO.setSkuId(item.getSkuId());
            cartItemVO.setQuantity(item.getQuantity());
            cartItemVO.setPrice(item.getPrice());
            cartItemVO.setSubtotal(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
            return cartItemVO;
        }).collect(Collectors.toList());

        cartVO.setItems(cartItemVOList);
        cartVO.setTotalPrice(
                cartItemVOList.stream()
                        .map(CartItemVO::getSubtotal)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
        );

        return cartVO;
    }
}