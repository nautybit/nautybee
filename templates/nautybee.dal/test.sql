CREATE TABLE `nautybee_prize` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `creator` int(11) NOT NULL COMMENT '创建人ID',
  `gmt_modified` datetime DEFAULT NULL COMMENT '更新时间',
  `modifier` int(11) NOT NULL COMMENT '更新人ID',
  `is_deleted` char(1) DEFAULT 'N' COMMENT '是否删除,Y删除，N未删除',
  `prize_origin` varchar(32) DEFAULT NULL COMMENT '奖品来源：ORDER(下单),RECOMMEND(推荐)',
  `origin_id` int(11) DEFAULT NULL COMMENT '来源ID',
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `prize_type` varchar(20) DEFAULT NULL COMMENT '奖品类型:amount（金额），thing（物品）',
  `prize_value` varchar(36) DEFAULT NULL COMMENT '奖品值',
  `prize_status` varchar(12) DEFAULT NULL COMMENT 'WLQ(未领取),YLQ(已领取)',
  `prize_remark` varchar(128) DEFAULT NULL COMMENT '奖品备注',
  PRIMARY KEY (`id`),
  KEY `idx_gmt_modified` (`gmt_modified`),
  KEY `idx_user_id_prize_origin` (`user_id`,`prize_status`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8mb4 COMMENT='奖品表';