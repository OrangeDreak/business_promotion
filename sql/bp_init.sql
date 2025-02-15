DROP TABLE IF EXISTS `bp_user`;
CREATE TABLE `bp_user` (
                            `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
                            `first_name` varchar(32) NOT NULL DEFAULT '' COMMENT '姓',
                            `last_name` varchar(32) NOT NULL DEFAULT '' COMMENT '名',
                            `nickname` varchar(32) NOT NULL DEFAULT '' COMMENT '昵称',
                            `user_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '用户类型 0：C端用户 1：后台用户',
                            `password` varchar(64) NOT NULL DEFAULT '' COMMENT '密码',
                            `avatar` varchar(255) NOT NULL DEFAULT '' COMMENT '头像',
                            `mobile` varchar(32) NOT NULL DEFAULT '' COMMENT '手机号',
                            `encrypted_mobile` varchar(64) NOT NULL DEFAULT '' COMMENT '加密手机号',
                            `email` varchar(64) NOT NULL DEFAULT '' COMMENT '邮箱',
                            `sex` tinyint(4) NOT NULL DEFAULT '1' COMMENT '性别:1男；2女',
                            `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
                            `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0正常；1冻结；2待激活；3已注销',
                            `is_delete` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除',
                            `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            PRIMARY KEY (`id`) USING BTREE,
                            KEY `idx_email` (`email`) USING BTREE,
                            KEY `idx_nickname` (`nickname`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=100083 DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

DROP TABLE IF EXISTS `bp_token`;
CREATE TABLE `bp_token` (
                            `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
                            `token` varchar(64) NOT NULL DEFAULT '' COMMENT 'token',
                            `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
                            `expire_time` int(11) NOT NULL DEFAULT '0' COMMENT '过期时间',
                            `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            PRIMARY KEY (`id`) USING BTREE,
                            KEY `idx_token` (`token`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='token表';

DROP TABLE IF EXISTS `bp_token`;
CREATE TABLE `bp_token` (
                            `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
                            `token` varchar(64) NOT NULL DEFAULT '' COMMENT 'token',
                            `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
                            `expire_time` int(11) NOT NULL DEFAULT '0' COMMENT '过期时间',
                            `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            PRIMARY KEY (`id`) USING BTREE,
                            KEY `idx_token` (`token`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='token表';

DROP TABLE IF EXISTS `bp_platform`;
CREATE TABLE `bp_platform` (
                            `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
                            `name` varchar(64) NOT NULL DEFAULT '' COMMENT '名称',
                            `name_en` varchar(64) NOT NULL DEFAULT '' COMMENT '名称en',
                            `label` varchar(256) NOT NULL DEFAULT '' COMMENT '标签',
                            `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '1正常；0失效',
                            `sort` int NOT NULL DEFAULT '0' COMMENT '排序',
                            `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='平台表';

DROP TABLE IF EXISTS `bp_product`;
CREATE TABLE `bp_product` (
                            `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
                            `title` varchar(64) NOT NULL DEFAULT '' COMMENT '标题',
                            `title_en` varchar(64) NOT NULL DEFAULT '' COMMENT '标题en',
                            `image` varchar(256) NOT NULL DEFAULT '' COMMENT '图片',
                            `desc` varchar(256) NOT NULL DEFAULT '' COMMENT '描述',
                            `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '1上架；0下架',
                            `price` int NOT NULL DEFAULT '0' COMMENT '价格 rmb 分',
                            `platform` int NOT NULL DEFAULT '0' COMMENT '平台',
                            `sort` int NOT NULL DEFAULT '0' COMMENT '排序',
                            `is_delete` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除',
                            `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品表';

DROP TABLE IF EXISTS `bp_product_sku`;
CREATE TABLE `bp_product_sku` (
                            `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
                            `product_id` int NOT NULL DEFAULT '0' COMMENT '商品ID',
                            `attributes` varchar(128) NOT NULL DEFAULT '' COMMENT '规格属性',
                            `attributes_en` varchar(128) NOT NULL DEFAULT '' COMMENT '规格属性en',
                            `image` varchar(256) NOT NULL DEFAULT '' COMMENT '图片',
                            `total` int NOT NULL DEFAULT '0' COMMENT '总库存',
                            `stock` int NOT NULL DEFAULT '0' COMMENT '可售卖库存',
                            `sold` int NOT NULL DEFAULT '0' COMMENT '售卖数',
                            `price` int NOT NULL DEFAULT '0' COMMENT '价格 rmb 分',
                            `sort` int NOT NULL DEFAULT '0' COMMENT '排序',
                            `is_delete` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除',
                            `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品sku表';


