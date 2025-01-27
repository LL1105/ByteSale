CREATE
DATABASE IF NOT EXISTS byte_sale_cart CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
DROP TABLE IF EXISTS cart;
CREATE TABLE `cart`
(
    id             bigint auto_increment comment '主键id'
        primary key,
    user_id        bigint not null comment '用户id',
    cart_id bigint not null comment '购物车id'
)
    COMMENT='购物车';

DROP TABLE IF EXISTS cart_item;
CREATE TABLE `cart_item`
(
    id             bigint auto_increment comment '主键id'
        primary key,
    cart_id bigint        not null comment '购物车id',
    sku_id         bigint        not null comment 'SKU=Stock Keeping Unit（库存量单位）',
    spu_id         bigint        not null comment 'SPU=Standard Product Unit （标准产品单位）',
    store_id       bigint        not null comment '商家id',
    quantity       int default 0 not null comment '商品数量',
    price          int default 0 not null comment '商品价格+单价',
    discount_id    bigint        null comment '优惠券id'
)
    COMMENT='购物车条目';


