package com.nautybit.nautybee.biz.order;

import java.util.List;
import java.util.Map;

import com.nautybit.nautybee.entity.order.Order;

public interface OrderService {
  List<Order> getAll();

  Order getById(Long id);

  boolean save(Order order);

  boolean deleteById(Long id);

  int deleteByIds(Long[] ids);


}
