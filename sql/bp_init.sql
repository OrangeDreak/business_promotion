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
                            `title` varchar(128) NOT NULL DEFAULT '' COMMENT '标题',
                            `title_en` varchar(128) NOT NULL DEFAULT '' COMMENT '标题en',
                            `image` varchar(256) NOT NULL DEFAULT '' COMMENT '图片',
                            `desc` varchar(256) NOT NULL DEFAULT '' COMMENT '描述',
                            `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '1上架；0下架',
                            `price` int NOT NULL DEFAULT '0' COMMENT '价格 rmb 分',
                            `platform` int NOT NULL DEFAULT '0' COMMENT '平台',
                            `sort` int NOT NULL DEFAULT '0' COMMENT '排序',
                            `is_delete` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除',
                            `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            PRIMARY KEY (`id`) USING BTREE,
                            KEY `idx_platform` (`platform`) USING BTREE
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
                            PRIMARY KEY (`id`) USING BTREE,
                            KEY `idx_product_id` (`product_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品sku表';


DROP TABLE IF EXISTS `bp_order`;
CREATE TABLE `bp_order` (
                            `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
                            `order_no` varchar(128) NOT NULL DEFAULT '' COMMENT '订单编号',
                            `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
                            `platform` tinyint(4) NOT NULL DEFAULT '0' COMMENT '平台',
                            `order_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '订单状态',
                            `subtotal` bigint(20) NOT NULL DEFAULT '0' COMMENT '商品合计',
                            `currency` varchar(32) NOT NULL DEFAULT '' COMMENT '币种',
                            `out_subtotal` bigint(20) NOT NULL DEFAULT '0' COMMENT '外币商品合计',
                            `ext` text COMMENT '扩展字段',
                            `is_delete` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除',
                            `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            PRIMARY KEY (`id`) USING BTREE,
                            KEY `idx_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

DROP TABLE IF EXISTS `bp_order_item`;
CREATE TABLE `bp_order_item` (
                            `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
                            `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
                            `platform` tinyint(4) NOT NULL DEFAULT '0' COMMENT '平台',
                            `order_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '主订单id',
                            `product_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '商品ID',
                            `sku_id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'SKU ID',
                            `product_name` varchar(100) DEFAULT '' COMMENT '商品名称冗余字段',
                            `sku_count` bigint(20) NOT NULL DEFAULT '0' COMMENT '商品数量',
                            `subtotal` bigint(20) NOT NULL DEFAULT '0' COMMENT '商品合计',
                            `real_subtotal` bigint(20) NOT NULL DEFAULT '0' COMMENT '实际子订单金额',
                            `sub_order_status` bigint(20) DEFAULT '0' COMMENT '状态',
                            `remark` varchar(512) NOT NULL DEFAULT '' COMMENT '备注',
                            `is_delete` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除',
                            `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            PRIMARY KEY (`id`) USING BTREE,
                            KEY `idx_order_id` (`order_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='子订单表';

DROP TABLE IF EXISTS `bp_product_snapshot`;
CREATE TABLE `bp_product_snapshot` (
                            `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
                            `order_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '主订单id',
                            `sub_order_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '子订单id',
                            `platform` tinyint(4) NOT NULL DEFAULT '0' COMMENT '平台',
                            `product_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '商品ID',
                            `sku_id` bigint(20) NOT NULL DEFAULT '0' COMMENT 'SKU ID',
                            `title` varchar(128) NOT NULL DEFAULT '' COMMENT '标题',
                            `title_en` varchar(128) NOT NULL DEFAULT '' COMMENT '标题en',
                            `attributes` varchar(128) NOT NULL DEFAULT '' COMMENT '规格属性',
                            `attributes_en` varchar(128) NOT NULL DEFAULT '' COMMENT '规格属性en',
                            `price` int NOT NULL DEFAULT '0' COMMENT '价格 rmb 分',
                            `is_delete` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除',
                            `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            PRIMARY KEY (`id`) USING BTREE,
                            KEY `idx_order_id` (`order_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品快照表';

DROP TABLE IF EXISTS `bp_exchange_rate`;
CREATE TABLE `bp_exchange_rate` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `exchange_rate` decimal(10,4) NOT NULL COMMENT '汇率',
  `exchange_rate_type` int(3) DEFAULT '0' COMMENT '汇率类型 0 越南盾汇率 1美元汇率 ',
  `exchange_rate_date` varchar(20) NOT NULL COMMENT '哪天的汇率',
  `is_delete` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uni_exchange_type_date` (`exchange_rate_type`,`exchange_rate_date`) USING BTREE COMMENT '汇率类型、日期唯一'
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COMMENT='每天汇率记录表';