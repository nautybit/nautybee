package com.nautybit.nautybee.biz.order;

import java.util.List;
import java.util.Map;

import com.nautybit.nautybee.common.param.order.OrderParam;
import com.nautybit.nautybee.entity.order.Order;

public interface OrderService {
  List<Order> getAll();

  Order getById(Long id);

  boolean save(Order order);

  boolean deleteById(Long id);

  int deleteByIds(Long[] ids);

    String generateTradeNo();
    List<Order> queryByIds(List<Long> orderIdList);
    Order createOrder(OrderParam orderParam);
    void updatePayStatus(String orderSn,String status);
}
