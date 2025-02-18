DROP TABLE IF EXISTS `byte_sale`.`user_info`;
CREATE TABLE `byte_sale`.`user_info`
(
    `id`    bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `username` varchar(128) NOT NULL UNIQUE COMMENT '用户名',
    `avatar` varchar(256) NOT NULL DEFAULT '' COMMENT '头像',
    `email` varchar(256) UNIQUE COMMENT '邮箱',
    `password` varchar(1024) NOT NULL DEFAULT '' COMMENT '密码',
    `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '账号状态 (0: 正常, 1: 停用)',
    `del_flag` tinyint(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '删除标志 (0: 正常, 1: 删除)',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by` bigint(20) NOT NULL DEFAULT '0' NULL COMMENT '创建者',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `update_by` bigint(20) NOT NULL DEFAULT '0' COMMENT '修改者',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='用户信息表';