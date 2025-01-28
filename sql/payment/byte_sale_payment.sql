CREATE
DATABASE IF NOT EXISTS byte_sale_payment CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE byte_sale_payment;
DROP TABLE IF EXISTS pay_bill;
CREATE TABLE `pay_bill`
(
    `id`              bigint(20) NOT NULL COMMENT '主键id',
    `pay_number`      varchar(64)  DEFAULT NULL COMMENT '支付流水号',
    `out_order_no`    varchar(64)    NOT NULL COMMENT '商户订单号',
    `pay_channel`     varchar(64)  DEFAULT NULL COMMENT '支付渠道',
    `pay_scene`       varchar(64)  DEFAULT NULL COMMENT '支付环境',
    `subject`         varchar(512) DEFAULT NULL COMMENT '订单标题',
    `trade_number`    varchar(256) DEFAULT NULL COMMENT '三方交易凭证号',
    `pay_amount`      decimal(10, 0) NOT NULL COMMENT '支付金额',
    `pay_bill_type`   int(11) NOT NULL COMMENT '支付种类 详细见枚举PayBillType',
    `pay_bill_status` int(11) NOT NULL DEFAULT '1' COMMENT '账单支付状态 详细见枚举PayBillStatus',
    `pay_time`        datetime     DEFAULT NULL COMMENT '支付时间',
    `create_time`     datetime       NOT NULL COMMENT '创建时间',
    `edit_time`       datetime       NOT NULL COMMENT '修改时间',
    `status`          tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态 1：未删除 0：删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `pay_bill_out_order_no_IDX` (`out_order_no`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付表';

DROP TABLE IF EXISTS refund_bill;
CREATE TABLE `refund_bill`
(
    `id`            bigint(20) NOT NULL COMMENT '主键id',
    `out_order_no`  varchar(64)    NOT NULL COMMENT '商户订单号',
    `pay_bill_id`   bigint(20) NOT NULL COMMENT '账单id',
    `refund_amount` decimal(10, 0) NOT NULL COMMENT '退款金额',
    `refund_status` int(11) NOT NULL DEFAULT '1' COMMENT '账单退款状态 1：未退款 2：已退款',
    `refund_time`   datetime    DEFAULT NULL COMMENT '退款时间',
    `reason`        varchar(50) DEFAULT NULL COMMENT '退款原因',
    `create_time`   datetime       NOT NULL COMMENT '创建时间',
    `edit_time`     datetime       NOT NULL COMMENT '修改时间',
    `status`        tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态 1：未删除 0：删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `refund_bill_out_order_no_IDX` (`out_order_no`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='退款表';


