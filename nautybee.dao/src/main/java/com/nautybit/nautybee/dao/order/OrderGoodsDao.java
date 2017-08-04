package com.nautybit.nautybee.dao.order;

import com.nautybit.nautybee.dao.base.BaseDao;
import com.nautybit.nautybee.dao.common.MyBatisRepository;
import com.nautybit.nautybee.entity.order.OrderGoods;

@MyBatisRepository
public interface OrderGoodsDao extends BaseDao<OrderGoods> {

    OrderGoods selectByOrderId(Long orderId);
    OrderGoods selectByOrderSn(String orderSn);
}
