CREATE
DATABASE IF NOT EXISTS byte_sale_cart CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
DROP TABLE IF EXISTS cart;
CREATE TABLE `cart` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `store_id` bigint(20) DEFAULT '0' COMMENT '商家id',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='购物车';

DROP TABLE IF EXISTS cart_item;
CREATE TABLE `cart_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `cart_id` bigint(20) NOT NULL COMMENT '购物车id',
  `sku_id` bigint(20) NOT NULL COMMENT 'SKU=Stock Keeping Unit（库存量单位）',
  `spu_id` bigint(20) NOT NULL COMMENT 'SPU=Standard Product Unit（标准产品单位）',
  `store_id` bigint(20) NOT NULL COMMENT '商家id',
  `quantity` int(11) NOT NULL DEFAULT '1' COMMENT '商品数量',
  `price` decimal(10, 2) NOT NULL COMMENT '商品单价',
  `total_price` decimal(10, 2) NOT NULL COMMENT '商品总价',
  `discount_id` bigint(20) DEFAULT NULL COMMENT '优惠券id',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态 1：有效 0：无效',
  PRIMARY KEY (`id`),
  KEY `idx_cart_id` (`cart_id`),
  KEY `idx_sku_id` (`sku_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='购物车条目';


