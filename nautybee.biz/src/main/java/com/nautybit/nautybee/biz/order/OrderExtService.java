package com.nautybit.nautybee.biz.order;

import java.util.List;
import java.util.Map;

import com.nautybit.nautybee.common.param.order.OrderParam;
import com.nautybit.nautybee.entity.order.OrderExt;

public interface OrderExtService {
  List<OrderExt> getAll();

  OrderExt getById(Long id);

  boolean save(OrderExt orderExt);

  boolean deleteById( Long id);

  int deleteByIds(Long[] ids);

    void createOrderExt(OrderParam orderParam);

    OrderExt selectByOrderSn(String orderSn);
}
