package com.nautybit.nautybee.biz.order;

import java.util.List;
import java.util.Map;

import com.nautybit.nautybee.common.param.order.OrderParam;
import com.nautybit.nautybee.entity.order.OrderGoods;

public interface OrderGoodsService {
  List<OrderGoods> getAll();

  OrderGoods getById(Long id);

  boolean save(OrderGoods orderGoods);

  boolean deleteById(Long id);

  int deleteByIds(Long[] ids);

    void createOrderGoods(OrderParam orderParam);
}
