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

    /**
     * 添加商品到购物车
     *
     * @param dto 购物车添加参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addItem(CartAddDTO dto) {
        Long userId = dto.getUserId();
        Long skuId = dto.getSkuId();
        Integer quantity = dto.getQuantity();

        log.info("开始处理添加购物车请求 - 用户ID: {}, 商品SKU: {}, 数量: {}", userId, skuId, quantity);

        // 1. 校验库存
        verifyProductStock(skuId, quantity);

        // 2. 获取商品价格
        BigDecimal price = getProductPrice(skuId);

        // 3. 查找或创建购物车
        Cart cart = findOrCreateCart(userId);
        log.info("购物车处理结果 - 购物车ID: {}", cart.getId());

        // 4. 处理购物车条目
        handleCartItem(cart, userId, skuId, quantity, price);

        // 5. 获取并缓存购物车信息
        CartVO cartVO = fetchCartVO(userId);
        if (cartVO != null) {
            String redisKey = CART_REDIS_KEY_PREFIX + userId;
            redisTemplate.opsForValue().set(redisKey, cartVO, 30, TimeUnit.MINUTES);
        }

        log.info("用户 [{}] 成功添加商品 [{}] 到购物车，数量 [{}]", userId, skuId, quantity);
    }

    /**
     * 校验商品库存
     *
     * @param skuId     商品SKU ID
     * @param quantity  商品数量
     */
    private void verifyProductStock(Long skuId, Integer quantity) {
        Boolean stockCheck = productApi.checkStock(skuId, quantity);
        if (!stockCheck) {
            log.warn("商品 [{}] 库存不足，无法加入购物车", skuId);
            throw new RuntimeException("商品库存不足");
        }
    }

    /**
     * 获取商品价格
     *
     * @param skuId 商品SKU ID
     * @return 商品价格
     */
    private BigDecimal getProductPrice(Long skuId) {
        BigDecimal price = productApi.getPrice(skuId);
        if (Objects.isNull(price)) {
            log.error("无法获取商品 [{}] 的价格", skuId);
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
    private Cart findOrCreateCart(Long userId) {
        Cart cart = cartMapper.selectByUserId(userId);
        if (cart == null) {
            log.info("创建新购物车 - 用户ID: {}", userId);
            cart = Cart.builder()
                    .userId(userId)
                    .storeId(0L)  // 默认商家ID
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            log.info("待插入的购物车: {}", cart);
            int insertResult = cartMapper.insert(cart);
            log.info("购物车插入结果: {}", insertResult);

            if (insertResult == 0) {
                log.error("购物车创建失败");
                throw new RuntimeException("无法创建购物车");
            }
        }
        return cart;
    }

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
        // 查询是否已存在相同商品的购物车项
        CartItem cartItem = cartItemMapper.selectByCartIdAndSkuId(cart.getId(), skuId);

        // 计算总价
        BigDecimal totalPrice = price.multiply(BigDecimal.valueOf(quantity));

        if (cartItem == null) {
            // 创建新的购物车项
            cartItem = CartItem.builder()
                    .cartId(cart.getId())
                    .skuId(skuId)
                    .spuId(getSpuIdFromSkuId(skuId))
                    .storeId(getStoreIdFromSkuId(skuId))
                    .quantity(quantity)
                    .price(price)
                    .totalPrice(totalPrice)
                    .status(1)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            cartItemMapper.insert(cartItem);
            log.info("新增购物车项 - SKU: {}, 数量: {}", skuId, quantity);
        } else {
            // 更新已存在的购物车项
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItem.setTotalPrice(price.multiply(BigDecimal.valueOf(cartItem.getQuantity())));
            cartItem.setUpdatedAt(LocalDateTime.now());

            cartItemMapper.updateById(cartItem);
            log.info("更新购物车项 - SKU: {}, 新数量: {}", skuId, cartItem.getQuantity());
        }
    }

    /**
     * 获取购物车详细信息
     *
     * @param userId 用户ID
     * @return 购物车VO对象
     */
    private CartVO fetchCartVO(Long userId) {
        Cart cart = cartMapper.selectByUserId(userId);
        if (cart == null) {
            return null;
        }

        // 查询购物车项
        LambdaQueryWrapper<CartItem> queryWrapper = Wrappers.lambdaQuery(CartItem.class)
                .eq(CartItem::getCartId, cart.getId())
                .eq(CartItem::getStatus, 1);  // 仅查询有效的购物车项
        List<CartItem> cartItems = cartItemMapper.selectList(queryWrapper);

        // 构建购物车 VO
        CartVO cartVO = new CartVO();
        cartVO.setUserId(userId);

        List<CartItemVO> cartItemVOList = cartItems.stream().map(item -> {
            CartItemVO cartItemVO = new CartItemVO();
            cartItemVO.setSkuId(item.getSkuId());
            cartItemVO.setSpuId(item.getSpuId());
            cartItemVO.setQuantity(item.getQuantity());
            cartItemVO.setPrice(item.getPrice());
            cartItemVO.setSubtotal(item.getTotalPrice());
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

    /**
     * 根据SKU ID获取SPU ID
     *
     * @param skuId SKU ID
     * @return SPU ID
     */
    private Long getSpuIdFromSkuId(Long skuId) {
        // TODO: 从商品服务获取SPU ID
        // 这里需要调用商品服务的接口获取实际的SPU ID
        return 0L;
    }

    /**
     * 根据SKU ID获取商家ID
     *
     * @param skuId SKU ID
     * @return 商家ID
     */
    private Long getStoreIdFromSkuId(Long skuId) {
        // TODO: 从商品服务获取商家ID
        // 这里需要调用商品服务的接口获取实际的商家ID
        return 0L;
    }
}