package com.nautybit.nautybee.biz.order;

import java.util.List;
import java.util.Map;

import com.nautybit.nautybee.entity.order.PayOrder;

public interface PayOrderService {
  List<PayOrder> getAll();

  PayOrder getById(Long id);

  boolean save(PayOrder payOrder);

  boolean deleteById(Long id);

  int deleteByIds(Long[] ids);


}
