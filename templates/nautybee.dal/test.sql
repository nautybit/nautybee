CREATE TABLE `flora_lottery_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `creator` int(11) DEFAULT NULL COMMENT '创建人id',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier` int(11) DEFAULT NULL COMMENT '修改人id',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  `is_deleted` char(1) DEFAULT NULL COMMENT '是否删除',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `lottery_id` int(11) NOT NULL COMMENT '抽奖id',
  `ticket_sn` text COMMENT '券号，逗号分隔',
  `num` int(8) DEFAULT NULL COMMENT '一次购买数量',
  `trade_no` varchar(64) DEFAULT NULL COMMENT '交易流水号',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8mb4 COMMENT='抽奖用户表';