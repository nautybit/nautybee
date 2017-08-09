CREATE TABLE `nautybee_spu_chain` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `creator` int(11) DEFAULT NULL COMMENT '创建人id',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier` int(11) DEFAULT NULL COMMENT '修改人id',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  `is_deleted` char(1) DEFAULT NULL COMMENT '是否删除',
  `head_id` int(11) DEFAULT NULL COMMENT '连报课程spu_id',
  `node_id` int(11) DEFAULT NULL COMMENT '映射课程spu_id',
  `status` varchar(20) DEFAULT NULL COMMENT '映射课程状态(YXK:已选课,WXK:未选课)',
  `order_str` varchar(8) DEFAULT NULL COMMENT '排序',
  `memo` varchar(256) DEFAULT NULL COMMENT '说明',
  PRIMARY KEY (`id`),
  KEY `idx_head_id` (`head_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8mb4 COMMENT='连报课程映射表';