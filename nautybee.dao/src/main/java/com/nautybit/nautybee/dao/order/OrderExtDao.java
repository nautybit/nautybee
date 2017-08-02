package com.nautybit.nautybee.dao.order;

import com.nautybit.nautybee.dao.base.BaseDao;
import com.nautybit.nautybee.dao.common.MyBatisRepository;
import com.nautybit.nautybee.entity.order.OrderExt;

@MyBatisRepository
public interface OrderExtDao extends BaseDao<OrderExt> {
    OrderExt selectByOrderSn(String orderSn);


}
